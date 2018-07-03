from locust import TaskSet, task
import json
import requests as req
import random as rd
from datetime import timedelta


class FuncionarioUtilizadores(TaskSet):

    @task(1)
    def get_clientes(self):
        r = self.parent.client.get("/utilizadores/clientes",
                                   headers=self.parent.MY_AUTH_HEADER)
        if r.status_code == req.status_codes.codes.ok:
            self.parent.CLIENTES = json.loads(r.text)

    @task(1)
    def get_cliente(self):
        if len(self.parent.CLIENTES) == 0:
            self.get_clientes()

        if len(self.parent.CLIENTES) > 0:
            id = rd.choice(self.parent.CLIENTES)["id"]

            r = self.parent.client.get("/utilizadores/clientes/" + str(id),
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/utilizadores/clientes/{id}")


class FuncionarioPromocoes(TaskSet):

    @task(10)
    def get_promocoes(self):
        r = self.parent.client.get("/promocoes",
                                   headers=self.parent.MY_AUTH_HEADER)
        if r.status_code == req.status_codes.codes.ok:
            self.parent.PROMOCOES = json.loads(r.text)

    @task(10)
    def get_promocao(self):
        if len(self.parent.PROMOCOES) == 0:
            self.get_promocoes()

        if len(self.parent.PROMOCOES) > 0:
            id = rd.choice(self.parent.PROMOCOES)["id"]
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
                self.parent.PROM_CRIADAS.append(r_json)

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
                self.parent.PROM_CRIADAS.append(r_json)

    @task(0)
    def delete_promocao(self):
        if len(self.parent.PROM_CRIADAS) > 0:
            prom_id = rd.choice(self.parent.PROM_CRIADAS)["id"]

            r = self.parent.client.delete("/promocoes/apagar",
                                          params={"id": prom_id},
                                          headers=self.parent.MY_AUTH_HEADER,
                                          name="/promocoes/apagar?id={id}")


class FuncionarioProdutos(TaskSet):

    def get_categorias(self):
        r = self.parent.client.get("/categorias",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.CATEGORIAS = json.loads(r.text)

    def get_produtos(self):
        r = self.parent.client.get("/produtos",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.PRODUTOS = json.loads(r.text)

    @task(10)
    def editar_produto(self):
        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            prod = rd.choice(self.parent.PRODUTOS)
            body = {
                "codigo": prod["codigo"],
                "descricao": self.parent.MY_FAKER.sentence(nb_words=3)
            }

            r = self.parent.client.put("/produtos/editar",
                                       json=body,
                                       headers=self.parent.MY_AUTH_HEADER)

    @task(5)
    def criar_produto(self):
        if len(self.parent.CATEGORIAS) == 0:
            self.get_categorias()

        if len(self.parent.CATEGORIAS) > 0:
            body = {
                "nome": self.parent.MY_FAKER.sentence(nb_words=3).replace('.',
                                                                          ''),
                "descricao": self.parent.MY_FAKER.sentence(nb_words=4),
                "precoBase": str(rd.uniform(1.0, 50.0)),
                "stock": str(rd.randint(0, 50)),
                "categoria": str(rd.choice(self.parent.CATEGORIAS)["descricao"])
            }

            r = self.parent.client.post("/produtos/criar",
                                        json=body,
                                        headers=self.parent.MY_AUTH_HEADER)

    @task(0)
    def remover_produto(self):
        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            prod = rd.choice(self.parent.PRODUTOS)
            cod_prod = prod["codigo"]
            r = self.parent.client.delete("/produtos/apagar",
                                          params={"codigo": str(cod_prod)},
                                          headers=self.parent.MY_AUTH_HEADER,
                                          name="/produtos/apagar?codigo={codigo}")


    @task(5)
    def get_mais_vendidos_detail(self):
        qtd = rd.randint(10, 20)
        r = self.parent.client.get("/produtos/maisVendidosDetail/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/produtos/maisVendidosDetail/{qtd}")


class FuncionarioCategoria(TaskSet):

    @task(20)
    def get_categorias(self):
        r = self.parent.client.get("/categorias",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.CATEGORIAS = json.loads(r.text)

    @task(2)
    def criar_categoria(self):

        r = self.parent.client.post("/categorias/criar",
                                    json={
                                        "descricao": self.parent.MY_FAKER.sentence(
                                            nb_words=3).replace('.', '')},
                                    headers=self.parent.MY_AUTH_HEADER)

    @task(0)
    def delete_categoria(self):
        if len(self.parent.CATEGORIAS) == 0:
            self.get_categorias()

        if len(self.parent.CATEGORIAS) > 0:
            cat_desc = rd.choice(self.parent.CATEGORIAS)["descricao"]

            r = self.parent.client.delete("/categorias/apagar",
                                          params={"descricao": cat_desc},
                                          headers=self.parent.MY_AUTH_HEADER,
                                          name="/categorias/apagar?descricao={descricao}")


class FuncionarioEncomendas(TaskSet):

    @task(1)
    def get_encomendas(self):
        r = self.parent.client.get("/encomendas",
                                   headers=self.parent.MY_AUTH_HEADER)
        if r.status_code == req.status_codes.codes.ok:
            self.parent.ENCOMENDAS = json.loads(r.text)

    @task(1)
    def get_ultimas(self):
        qtd = rd.randint(10, 20)

        r = self.parent.client.get("/encomendas/ultimas/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/encomendas/ultimas/{qtd}")

    @task(1)
    def get_encomenda(self):

        if len(self.parent.ENCOMENDAS) == 0:
            self.get_encomendas()

        if len(self.parent.ENCOMENDAS) > 0:
            enc_id = rd.choice(self.parent.ENCOMENDAS)["id"]

            r = self.parent.client.get("/encomendas/" + str(enc_id),
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/encomendas/{id}")

    @task(1)
    def alterar_estado(self):

        if len(self.parent.ENCOMENDAS) == 0:
            self.get_encomendas()

        if len(self.parent.ENCOMENDAS) > 0:
            enc = rd.choice(self.parent.ENCOMENDAS)

            enc_id, enc_estado = enc["id"], enc["estado"]

            r = self.parent.client.put("/encomendas/alterarEstado",
                                       json={"id": enc_id, "estado": enc_estado},
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/encomendas/alterarEstado")
