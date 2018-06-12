package mystore.services;

import mystore.models.Produto;

import java.util.List;

public interface ProdutoService extends GenericService<Produto> {

    List<Produto> list();

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos();

    List<Produto> listPromocao();
}
