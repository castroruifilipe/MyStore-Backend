package mystore.daos;

import mystore.models.Cliente;
import org.springframework.stereotype.Repository;

@Repository("clienteDAO")
public class ClienteDAOImpl extends GenericDAOImpl<Cliente, Long> implements ClienteDAO {
}
