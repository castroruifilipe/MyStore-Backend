package mystore.services;

import mystore.daos.FuncionarioDAO;
import mystore.models.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class FuncionarioServiceImpl implements FuncionarioService {

    @Autowired
    protected FuncionarioDAO funcionarioDAO;


    @Override
    public void save(Funcionario objToSave) {
        funcionarioDAO.save(objToSave);
    }

    @Override
    public List<Funcionario> list() {
        return funcionarioDAO.getAll();
    }

    @Override
    public Optional<Funcionario> get(long uid) {
        return funcionarioDAO.find(uid);
    }

}
