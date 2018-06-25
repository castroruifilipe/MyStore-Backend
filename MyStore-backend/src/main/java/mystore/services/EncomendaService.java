package mystore.services;

import mystore.models.*;
import mystore.models.enums.MetodoPagamento;

import java.util.List;
import java.util.Optional;

public interface EncomendaService {

    void save(Encomenda encomenda);

    List<Encomenda> list();

    Optional<Encomenda> get(long id);

    Encomenda checkout(Cliente cliente, Morada moradaEnvio, Carrinho carrinho, MetodoPagamento metodoPagamento);
}
