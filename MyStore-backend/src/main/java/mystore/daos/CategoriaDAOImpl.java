package mystore.daos;

import mystore.models.Categoria;
import org.springframework.stereotype.Repository;

@Repository("categoriaDAO")
public class CategoriaDAOImpl extends GenericDAOImpl<Categoria, Long> implements CategoriaDAO {

}
