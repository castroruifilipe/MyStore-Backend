package mystore.services;

import mystore.daos.ClienteDAO;
import mystore.models.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    protected ClienteDAO clienteDAO;

    @Override
    public void save(Cliente objToSave) {
        clienteDAO.save(objToSave);
    }
}
