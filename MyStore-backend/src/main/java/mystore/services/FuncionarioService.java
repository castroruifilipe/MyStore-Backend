package mystore.services;

import mystore.models.Funcionario;

import java.util.List;

public interface FuncionarioService {

    void save(Funcionario funcionario);

    List<Funcionario> list();
}
