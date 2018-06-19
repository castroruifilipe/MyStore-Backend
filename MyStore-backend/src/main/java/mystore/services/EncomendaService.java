package mystore.services;

import mystore.models.Encomenda;

import java.util.List;
import java.util.Optional;

public interface EncomendaService {

    void save(Encomenda encomenda);

    List<Encomenda> list();

    Optional<Encomenda> get(long id);
}
