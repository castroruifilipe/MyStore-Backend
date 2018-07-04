#!/usr/bin/env bash
locust --no-reset-stats --host=https://mystore-backend.herokuapp.com NewFuncionarioLocust NewUserLocust AuthUserLocust
