package mystore.services;

import mystore.models.Categoria;
import mystore.models.Produto;
import mystore.models.Promocao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.AbstractMap.SimpleEntry;

public interface PromocaoService {

    void save(Promocao promocao);

    Optional<Promocao> get(long id);

    List<Promocao> list();

    Optional<SimpleEntry<Promocao, Double>> get(Produto produto);

    Promocao criar(String descricao, double desconto, LocalDate dataInicio, LocalDate dataFim, Categoria categoria);

    Promocao criar(String descricao, double desconto, LocalDate dataInicio, LocalDate dataFim, Set<Produto> produtos);
}
