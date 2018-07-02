from locust import TaskSet, task
import requests as req
import json
import random as rd


class ClienteUtilizadores(TaskSet):
    @task(1)
    def dados(self):
        r = self.parent.client.get("/utilizadores/dados",
                                   headers=self.parent.MY_AUTH_HEADER)

    @task(1)
    def editarDados(self):
        new_address = self.parent.MY_FAKER.address().replace('\n', ' ')

        r = self.parent.client.put("/utilizadores/editarDados",
                                   json={'rua': new_address},
                                   headers=self.parent.MY_AUTH_HEADER)

    @task(1)
    def alterarPassword(self):
        r = self.parent.client.put("/utilizadores/alterarPassword",
                                   json={"oldPassword": "123",
                                         "newPassword": "123"},
                                   headers=self.parent.MY_AUTH_HEADER)

class ProdutosCliente(TaskSet):

    @task(1)
    def get_categorias(self):
        r = self.parent.client.get("/categorias",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.CATEGORIAS = json.loads(r.text)

    @task(1)
    def get_produtos(self):
        r = self.parent.client.get("/produtos",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.PRODUTOS = json.loads(r.text)

    @task(1)
    def get_produto(self):
        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            cod_prod = rd.choice(self.parent.PRODUTOS)["codigo"]
            r = self.parent.client.get("/produtos/" + str(cod_prod),
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/produtos/{codigo}")

    @task(5)
    def get_novidades(self):
        qtd = rd.randint(10, 20)
        r = self.parent.client.get("/produtos/novidades/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/produtos/novidades/{qtd}")

    @task(5)
    def get_mais_vendidos(self):
        qtd = rd.randint(10, 20)
        r = self.parent.client.get("/produtos/maisVendidos/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/produtos/maisVendidos/{qtd}")

    @task(5)
    def get_mais_vendidos_detail(self):
        qtd = rd.randint(10, 20)
        r = self.parent.client.get("/produtos/maisVendidosDetail/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/produtos/maisVendidosDetail/{qtd}")

    @task(5)
    def get_em_promocao(self):
        qtd = rd.randint(10, 20)
        r = self.parent.client.get("/produtos/emPromocao/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/produtos/emPromocao/{qtd}")

    @task(5)
    def get_relacionados(self):
        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            cod_prod = rd.choice(self.parent.PRODUTOS)["codigo"]

            qtd = rd.randint(10, 20)
            r = self.parent.client.get("/produtos/relacionados/" + str(qtd),
                                       params={"codigo": cod_prod},
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/produtos/relacionados/{qtd}")

    @task(5)
    def get_produtos_pag(self):
        if len(self.parent.CATEGORIAS) == 0:
            self.get_categorias()

        if len(self.parent.CATEGORIAS) > 0:
            cod_cat = rd.choice(self.parent.CATEGORIAS)["id"]

            pag = 1
            size = rd.randint(10, 20)
            r = self.parent.client.get("/produtos/categoria",
                                       params={"pagina": pag,
                                               "size": size,
                                               "categoria": cod_cat},
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/produtos/categoria")

    @task(5)
    def search_produtos_categoria(self):
        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            prod = rd.choice(self.parent.PRODUTOS)
            nome, id_cat = prod["nome"], prod["categoria"]["id"]

            r = self.parent.client.get("/produtos/search/categoria",
                                       params={"value": nome,
                                               "categoria": id_cat},
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/produtos/search/categoria")

    @task(5)
    def search_produtos(self):
        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            nome_prod = rd.choice(self.parent.PRODUTOS)["nome"]

            r = self.parent.client.get("/produtos/search",
                                       params={"value": nome_prod},
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/produtos/search")


