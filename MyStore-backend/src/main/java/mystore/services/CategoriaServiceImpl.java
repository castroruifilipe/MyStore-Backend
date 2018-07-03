package mystore.services;

import mystore.daos.CategoriaDAO;
import mystore.models.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {


    @Autowired
    protected CategoriaDAO categoriaDAO;


    @Override
    public void save(Categoria categoria) {
        Optional<Categoria> optionalCategoria = categoriaDAO.find(categoria.getDescricao());
        if (!optionalCategoria.isPresent()) {
            categoriaDAO.save(categoria);
        }
    }

    @Override
    public List<Categoria> list() {
        return categoriaDAO.getAll();
    }

    @Override
    public Optional<Categoria> get(String descricao) {
        return categoriaDAO.find(descricao);
    }

    @Override
    public void delete(Categoria categoria) {
        if (categoriaDAO.canDelete(categoria.getId())) {
            categoriaDAO.delete(categoria);
        } else {
            throw new EntityExistsException("Existem produtos para esta categoria");
        }
    }

}
