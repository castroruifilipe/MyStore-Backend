package mystore.daos;

import mystore.models.Categoria;
import mystore.models.Produto;

import java.util.List;

public interface ProdutoDAO extends GenericDAO<Produto, Long> {

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos(int quantidadeProdutos);

    List<Produto> listByCategoria(long categoria, int firstResult, int maxResults);

    List<Produto> related(Produto produto, int maxResults);

    List<Produto> search(String value);

    List<Produto> search(long categoria, String value);

    List<Produto> findPromocao();
}
