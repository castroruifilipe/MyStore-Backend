package mystore.services;

import mystore.models.*;
import mystore.models.enums.MetodoPagamento;

import java.util.List;
import java.util.Optional;

public interface EncomendaService {

    void save(Encomenda encomenda);

    List<Encomenda> list();

    List<Encomenda> listByCliente(long uid);

    Optional<Encomenda> get(long id);

    Encomenda checkout(Cliente cliente, Morada moradaEntrega, Carrinho carrinho, MetodoPagamento metodoPagamento);
}
