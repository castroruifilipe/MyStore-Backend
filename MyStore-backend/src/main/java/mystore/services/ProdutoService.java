package mystore.services;

import mystore.models.Produto;

import java.util.List;

public interface ProdutoService extends BasicService<Produto>{


    List<Produto> list();
    List<Produto> listPromocao();
}
