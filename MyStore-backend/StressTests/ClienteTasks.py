from locust import TaskSet, task
import requests as req
import json
import random as rd


class ClienteUtilizadores(TaskSet):
    @task(10)
    def dados(self):
        r = self.parent.client.get("/utilizadores/dados",
                                   headers=self.parent.MY_AUTH_HEADER)

    @task(2)
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

    @task(5)
    def get_produto(self):
        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            cod_prod = rd.choice(self.parent.PRODUTOS)["codigo"]
            r = self.parent.client.get("/produtos/" + str(cod_prod),
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/produtos/{codigo}")

    @task(10)
    def get_novidades(self):
        qtd = rd.randint(10, 20)
        r = self.parent.client.get("/produtos/novidades/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/produtos/novidades/{qtd}")

    @task(10)
    def get_mais_vendidos(self):
        qtd = rd.randint(10, 20)
        r = self.parent.client.get("/produtos/maisVendidos/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/produtos/maisVendidos/{qtd}")

    @task(10)
    def get_em_promocao(self):
        qtd = rd.randint(10, 20)
        r = self.parent.client.get("/produtos/emPromocao/" + str(qtd),
                                   headers=self.parent.MY_AUTH_HEADER,
                                   name="/produtos/emPromocao/{qtd}")

    @task(10)
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

            pag = 1
            size = rd.randint(10, 20)

            r = self.parent.client.get("/produtos/search/categoria",
                                       params={"value": nome,
                                               "categoria": id_cat,
                                               "pagina": pag,
                                               "size": size},
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/produtos/search/categoria?value={}&categoria={}&pagina={}&size={}")

    @task(5)
    def search_produtos(self):
        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            nome_prod = rd.choice(self.parent.PRODUTOS)["nome"]
            pag = 1
            size = rd.randint(10, 20)

            r = self.parent.client.get("/produtos/search",
                                       params={"value": nome_prod,
                                               "pagina": pag,
                                               "size": size},
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/produtos/search?value={}&pagina={}&size={}")


class EncomendasCliente(TaskSet):

    @task(1)
    def get_encomendas_cliente(self):
        r = self.parent.client.get("/encomendas/cliente",
                                   headers=self.parent.MY_AUTH_HEADER)


class CarrinhoCliente(TaskSet):

    def get_produtos(self):
        r = self.parent.client.get("/produtos",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.PRODUTOS = json.loads(r.text)

    @task(5)
    def get_carrinho(self):
        r = self.parent.client.get("/carrinho",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.CARRINHO = json.loads(r.text)

    @task(1)
    def operacoes_carrinho_checkout(self):
        self.add_carrinho()
        self.add_carrinho()
        self.update_carrinho()
        self.checkout()

    @task(3)
    def operacoes_carrinho_clear(self):
        self.add_carrinho()
        self.add_carrinho()
        self.remove_carrinho()
        self.add_carrinho()
        self.clear_carrinho()

    def add_carrinho(self):

        if len(self.parent.PRODUTOS) == 0:
            self.get_produtos()

        if len(self.parent.PRODUTOS) > 0:
            prod = rd.choice(self.parent.PRODUTOS)

            body = {"codigo": prod["codigo"],
                    "quantidade": rd.randint(1, 2)}

            r = self.parent.client.put("/carrinho/addProduto",
                                       json=body,
                                       headers=self.parent.MY_AUTH_HEADER)

            if r.status_code == req.status_codes.codes.ok:
                self.parent.CARRINHO = json.loads(r.text)

    def remove_carrinho(self):

        if len(self.parent.CARRINHO["linhasCarrinho"]) == 0:
            self.get_carrinho()

        if len(self.parent.CARRINHO["linhasCarrinho"]) > 0:
            cod_prod = rd.choice(self.parent.CARRINHO["linhasCarrinho"])["produto"]["codigo"]

            body = {"codigo": cod_prod}

            r = self.parent.client.put("/carrinho/removeProduto",
                                       json=body,
                                       headers=self.parent.MY_AUTH_HEADER)

            if r.status_code == req.status_codes.codes.ok:
                self.parent.CARRINHO = json.loads(r.text)

    def clear_carrinho(self):

        r = self.parent.client.put("/carrinho/clear",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.CARRINHO = json.loads(r.text)

    def clear_carrinho(self):

        r = self.parent.client.delete("/carrinho/clear",
                                      headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.CARRINHO = json.loads(r.text)

    def update_carrinho(self):

        self.get_carrinho()

        body = {}

        for linha_carrinho in self.parent.CARRINHO["linhasCarrinho"]:
            prod = linha_carrinho["produto"]

            body[prod["codigo"]] = linha_carrinho["quantidade"] + 1

        r = self.parent.client.put("/carrinho/update",
                                   json=body,
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            self.parent.CARRINHO = json.loads(r.text)

    def checkout(self):
        METODO_PAGAMENTO = rd.choice(["MULTIBANCO", "PAYPAL", "MBWAY", "COBRANCA"])
        morada = self.parent.MY_FAKER.address().replace('\n', ' ')

        r = self.parent.client.get("/carrinho",
                                   headers=self.parent.MY_AUTH_HEADER)

        if r.status_code == req.status_codes.codes.ok:
            print("Carrinho antes do checkout: " + r.text)
            print("Morada: {} Metodo Pagamento: {}".format(morada, METODO_PAGAMENTO))

        r = self.parent.client.post("/encomendas/checkout",
                                    json={"moradaEntrega": {
                                        "rua": morada,
                                        "localidade": "Gualtar",
                                        "codigoPostal": "4700-00"
                                    },
                                        "metodoPagamento": METODO_PAGAMENTO},
                                    headers=self.parent.MY_AUTH_HEADER)

        print("Resposta ao checkout: {}".format(r.text))
