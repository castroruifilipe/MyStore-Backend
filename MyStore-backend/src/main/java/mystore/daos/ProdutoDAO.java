package mystore.daos;

import mystore.models.Categoria;
import mystore.models.Produto;
import mystore.models.Promocao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProdutoDAO extends GenericDAO<Produto, Long> {

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos(int quantidadeProdutos);

    List<Object[]> maisVendidosDetail(int quantidadeProdutos);

    List<Produto> emPromocao(int quantidadeProdutos);

    List<Produto> listByCategoria(long categoria, int firstResult, int maxResults);

    List<Produto> related(Produto produto, int maxResults);

    List<Produto> search(String value);

    List<Produto> search(long categoria, String value);

    void updatePrices(Promocao promocao);

    double totalFaturado(long codigoProduto);
}
