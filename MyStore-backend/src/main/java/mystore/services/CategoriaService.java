package mystore.services;

import mystore.models.Categoria;

import java.util.List;
import java.util.Optional;


public interface CategoriaService extends GenericService<Categoria> {

    List<Categoria> list();

    Optional<Categoria> get(String descricao);
}
