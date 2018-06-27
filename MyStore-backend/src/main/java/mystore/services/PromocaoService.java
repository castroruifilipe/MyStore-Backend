package mystore.services;

import mystore.models.Produto;
import mystore.models.Promocao;

import java.util.List;
import java.util.Optional;

import static java.util.AbstractMap.SimpleEntry;

public interface PromocaoService {

    void save(Promocao promocao);

    Optional<Promocao> get(long id);

    List<Promocao> list();

    Optional<SimpleEntry<Promocao, Double>> get(Produto produto);
}
