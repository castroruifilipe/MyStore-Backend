package mystore.daos;

import mystore.models.Encomenda;

import java.util.List;

public interface EncomendaDAO extends GenericDAO<Encomenda, Long> {

    List<Encomenda> listByCliente(long uid);

    List<Encomenda> ultimas(int quantidadeEncomendas);
}
