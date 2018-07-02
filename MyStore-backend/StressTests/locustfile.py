from locust import HttpLocust, TaskSet, task
from faker import Faker
import json
from ClienteTasks import *
from FuncionarioTasks import *
import requests as req

class ClienteBehavior(TaskSet):

    tasks = {ClienteUtilizadores: 1, ProdutosCliente: 1}

    def on_start(self):
        self.MY_FAKER = Faker()
        self.PRODUTOS = []
        self.PROMOCOES = []
        self.PROM_CRIADAS = []
        self.CATEGORIAS = []
        self.CLIENTES = []
        self.login()

    def on_stop(self):
        self.logout()

    def login(self):
        r = self.client.post('/utilizadores/signin',
                             json={'email': 'andrerfcsantos@gmail.com',
                                   'password': '123'})

        if r.headers['Access-Token']:
            self.MY_AUTH_HEADER = {'Authorization': 'Bearer ' + r.headers['Access-Token']}
            self.DADOS_CLIENTE = json.loads(r.text)

    def logout(self):
        pass

class FuncionarioBehavior(TaskSet):

    tasks = {FuncionarioUtilizadores: 1,
             FuncionarioPromocoes: 1,
             FuncionarioProdutos: 1,
             FuncionarioCategoria: 1}

    def on_start(self):
        self.MY_FAKER = Faker()
        self.PRODUTOS = []
        self.PROMOCOES = []
        self.PROM_CRIADAS = []
        self.CATEGORIAS = []
        self.CLIENTES = []
        self.login()

    def on_stop(self):
        self.logout()

    def login(self):
        r = self.client.post('/utilizadores/signin',
                             json={'email': 'admin@mystore.pt',
                                   'password': 'admin'})

        if r.headers['Access-Token']:
            self.MY_AUTH_HEADER = {'Authorization': 'Bearer ' + r.headers['Access-Token']}
            self.DADOS_CLIENTE = json.loads(r.text)


    def logout(self):
        pass

class NewUserBehavior(TaskSet):

    tasks = {ClienteUtilizadores: 1}

    def on_start(self):
        self.MY_FAKER = Faker()
        self.PRODUTOS = []
        self.PROMOCOES = []
        self.PROM_CRIADAS = []
        self.CATEGORIAS = []
        self.CLIENTES = []
        self.create_user()

    def on_stop(self):
        self.logout()

    def create_user(self):
        email = self.MY_FAKER.email()

        r = self.client.post('/utilizadores/signup',
                             json={'email': email,
                                   'password': '123',
                                   'nome': self.MY_FAKER.name(),
                                   'role': 'CLIENTE'})

        if r.status_code == req.status_codes.codes.ok:
            r = self.client.post('/utilizadores/signin',
                                 json={'email': email,
                                       'password': '123'})

            if r.headers['Access-Token']:
                self.MY_AUTH_HEADER = {'Authorization': 'Bearer ' + r.headers['Access-Token']}
                self.DADOS_CLIENTE = json.loads(r.text)
        else:
            print("Tentativa de criar novo user que já exitia!")
            self.interrupt()

    def logout(self):
        pass



class ClienteLocust(HttpLocust):
    task_set = ClienteBehavior
    weight = 5

class FuncionarioLocust(HttpLocust):
    task_set = FuncionarioBehavior
    weight = 1

class NewUserLocust(HttpLocust):
    task_set = NewUserBehavior
    weight = 1
