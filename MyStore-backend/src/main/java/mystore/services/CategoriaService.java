package mystore.services;

import mystore.models.Categoria;

import java.util.List;
import java.util.Optional;


public interface CategoriaService {

    List<Categoria> list();

    void save(Categoria categoria);

    Optional<Categoria> get(String descricao);

    void delete(Categoria categoria);
}
