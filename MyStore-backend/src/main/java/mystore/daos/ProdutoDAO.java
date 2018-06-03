package mystore.daos;

import mystore.models.Produto;

import java.util.List;

public interface ProdutoDAO extends GenericDAO<Produto, Long> {
    public List<Produto> findPromocao();
}
