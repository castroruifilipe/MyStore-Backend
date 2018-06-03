package mystore.daos;

import mystore.models.Funcionario;
import org.springframework.stereotype.Repository;

@Repository("funcionarioDAO")
public class FuncionarioDAOImpl extends GenericDAOImpl<Funcionario, Long> implements FuncionarioDAO {
}
