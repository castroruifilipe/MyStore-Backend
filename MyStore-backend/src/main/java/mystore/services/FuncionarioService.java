package mystore.services;

import mystore.models.Funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioService {

    void save(Funcionario funcionario);

    List<Funcionario> list();

    Optional<Funcionario> get(long uid);
}
