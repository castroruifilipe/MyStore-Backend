package mystore.daos;

import mystore.models.Produto;

import java.util.List;

public interface ProdutoDAO extends GenericDAO<Produto, Long> {

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos(int quantidadeProdutos);

    List<Produto> findPromocao();
}
