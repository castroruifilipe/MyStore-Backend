package mystore.services;

import mystore.models.Categoria;

import java.util.List;


public interface CategoriaService extends GenericService<Categoria> {

    List<Categoria> list();

}
