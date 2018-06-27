package mystore.services;

import mystore.daos.ClienteDAO;
import mystore.models.Cliente;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    protected ClienteDAO clienteDAO;


    @Override
    public void save(Cliente objToSave) {
        clienteDAO.save(objToSave);
    }

    @Override
    @Transactional
    public Optional<Cliente> get(long uid) {
        Optional<Cliente> optionalCliente = clienteDAO.find(uid);
        optionalCliente.ifPresent(cliente -> Hibernate.initialize(cliente.getEncomendas()));
        return clienteDAO.find(uid);
    }

    @Override
    public List<Cliente> list() {
        return clienteDAO.getAll();
    }
}
