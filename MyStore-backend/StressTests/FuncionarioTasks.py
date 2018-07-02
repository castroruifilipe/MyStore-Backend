from locust import TaskSet, task
import json
import requests as req
import random as rd
from datetime import timedelta

class FuncionarioUtilizadores(TaskSet):

    def on_start(self):
        self.CLIENTES = []

    @task(1)
    def get_clientes(self):
        r = self.parent.client.get("/utilizadores/clientes",
                                   headers=self.parent.MY_AUTH_HEADER)
        if r.status_code == req.status_codes.codes.ok:
            self.CLIENTES = json.loads(r.text)

    @task(1)
    def get_cliente(self):
        if len(self.CLIENTES) > 0:
            id = self.CLIENTES[rd.randint(0, len(self.CLIENTES) - 1)]["id"]
            r = self.parent.client.get("/utilizadores/clientes/" + str(id),
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/utilizadores/clientes/{id}")

class FuncionarioPromocoes(TaskSet):

    def on_start(self):
        self.PROMOCOES = []
        self.PROM_CRIADAS = []

    @task(10)
    def get_promocoes(self):
        r = self.parent.client.get("/promocoes",
                                   headers=self.parent.MY_AUTH_HEADER)
        if r.status_code == req.status_codes.codes.ok:
            self.PROMOCOES = json.loads(r.text)

    @task(10)
    def get_promocao(self):
        r = self.parent.client.get("/promocoes",
                                   headers=self.parent.MY_AUTH_HEADER)
        if len(self.PROMOCOES) > 0:
            id = self.PROMOCOES[rd.randint(0, len(self.PROMOCOES) - 1)]["id"]
            r = self.parent.client.get("/promocoes/" + str(id),
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/promocoes/{id}")

    @task(3)
    def criar_promocao_categoria(self):
        r_categorias = self.parent.client.get("/categorias",
                                              headers=self.parent.MY_AUTH_HEADER)

        if r_categorias.status_code == req.status_codes.codes.ok:
            cats = json.loads(r_categorias.text)
            desc_cat = cats[rd.randint(0, len(cats) - 1)]["descricao"]

            description = self.parent.MY_FAKER.sentence(nb_words=3) \
                .replace('.', '')

            desconto = str(rd.uniform(0.10, 0.75))

            i_date = self.parent.MY_FAKER.future_date(end_date="+1d",
                                                      tzinfo=None)
            d_date = timedelta(days=rd.randint(1, 30))
            f_date = i_date + d_date

            i_date = i_date.strftime('%Y-%m-%d')
            f_date = f_date.strftime('%Y-%m-%d')

            body = {
                "descricao": description,
                "dataInicio": i_date,
                "dataFim": f_date,
                "desconto": desconto,
                "categoria": desc_cat
            }

            r = self.parent.client.post("/promocoes/criar",
                                        json=body,
                                        headers=self.parent.MY_AUTH_HEADER,
                                        name="/promocoes/criar (com categoria)")

            if r.status_code == req.status_codes.codes.ok:
                r_json = json.loads(r.text)
                self.PROM_CRIADAS.append(r_json)

    @task(1)
    def criar_promocao_produto(self):
        r_produtos = self.parent.client.get("/produtos",
                                            headers=self.parent.MY_AUTH_HEADER,
                                            name="/produtos")

        if r_produtos.status_code == req.status_codes.codes.ok:
            prods = json.loads(r_produtos.text)

            if len(prods) == 0:
                return

            prods_promo = rd.choices(prods, k=rd.randint(1, len(prods)))
            prods_promo = list(map(lambda x: x["codigo"], prods_promo))

            description = self.parent.MY_FAKER.sentence(nb_words=3) \
                .replace('.', '')

            desconto = str(rd.uniform(0.10, 0.75))

            i_date = self.parent.MY_FAKER.future_date(end_date="+1d",
                                                      tzinfo=None)
            d_date = timedelta(days=rd.randint(1, 30))
            f_date = i_date + d_date

            i_date = i_date.strftime('%Y-%m-%d')
            f_date = f_date.strftime('%Y-%m-%d')

            body = {
                "descricao": description,
                "dataInicio": i_date,
                "dataFim": f_date,
                "desconto": desconto,
                "produtos": prods_promo
            }

            r = self.parent.client.post("/promocoes/criar",
                                        json=body,
                                        headers=self.parent.MY_AUTH_HEADER,
                                        name="/promocoes/criar (com produtos)")

            if r.status_code == req.status_codes.codes.ok:
                r_json = json.loads(r.text)
                self.PROM_CRIADAS.append(r_json)

    @task(1)
    def delete_promocao(self):
        if len(self.PROM_CRIADAS) > 0:
            prom_id = rd.choice(self.PROM_CRIADAS)["id"]

            r = self.parent.client.delete("/promocoes/apagar",
                                          params={"id": prom_id},
                                          headers=self.parent.MY_AUTH_HEADER,
                                          name="/promocoes/apagar?id={id}")
