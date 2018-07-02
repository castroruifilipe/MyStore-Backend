from locust import TaskSet, task
import json
import requests as req
import random as rd

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
            id = self.CLIENTES[rd.randint(0, len(self.CLIENTES)-1)]["id"]
            r = self.parent.client.get("/utilizadores/clientes/"+str(id),
                                       headers=self.parent.MY_AUTH_HEADER,
                                       name="/utilizadores/clientes/{id}")


