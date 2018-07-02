from locust import TaskSet, task

class ClienteUtilizadores(TaskSet):
    @task(1)
    def dados(self):
        r = self.parent.client.get("/utilizadores/dados",
                                   headers=self.parent.MY_AUTH_HEADER)

    @task(1)
    def editarDados(self):
        new_address = self.parent.MY_FAKER.address().replace('\n',' ')


        r = self.parent.client.put("/utilizadores/editarDados",
                                   json={'rua': new_address},
                                   headers=self.parent.MY_AUTH_HEADER)

    @task(1)
    def alterarPassword(self):

        r = self.parent.client.put("/utilizadores/alterarPassword",
                                   json={"oldPassword": "123",
                                         "newPassword": "123"},
                                   headers=self.parent.MY_AUTH_HEADER)



# class ProdutosCliente(TaskSet):
#     @task(1)
#     def get_produtos(self):
#         r = self.parent.client.get("/produtos",
#                                    headers=self.parent.MY_AUTH_HEADER)