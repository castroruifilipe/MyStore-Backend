package mystore.services;

import mystore.models.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    void save(Cliente cliente);

    Optional<Cliente> get(long uid);

    List<Cliente> list();

}
