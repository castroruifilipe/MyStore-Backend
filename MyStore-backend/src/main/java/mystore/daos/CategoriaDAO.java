package mystore.daos;

import mystore.models.Categoria;

import java.util.Optional;

public interface CategoriaDAO extends GenericDAO<Categoria, Long>{

    Optional<Categoria> find(String descricao);

    boolean canDelete(long id);
}
