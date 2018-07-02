package mystore.services;

import mystore.models.*;
import mystore.models.enums.EstadoEncomenda;
import mystore.models.enums.MetodoPagamento;

import java.util.List;
import java.util.Optional;

public interface EncomendaService {

    void save(Encomenda encomenda);

    void update(Encomenda encomenda);

    List<Encomenda> list();

    List<Encomenda> ultimas(int quantidadeEncomendas);

    List<Encomenda> listByCliente(long uid);

    Optional<Encomenda> get(long id);

    Encomenda checkout(Cliente cliente, Morada moradaEntrega, Carrinho carrinho, MetodoPagamento metodoPagamento);

    Optional<Encomenda> alterarEstado(long id, EstadoEncomenda estadoEncomenda);
}
