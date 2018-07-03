package mystore.daos;

import mystore.models.Produto;
import mystore.models.Promocao;

import java.util.List;

public interface ProdutoDAO extends GenericDAO<Produto, Long> {

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos(int quantidadeProdutos);

    List<Produto> emPromocao(int quantidadeProdutos);

    List<Produto> listByCategoria(long categoria, int firstResult, int maxResults);

    List<Produto> related(Produto produto, int maxResults);

    List<Produto> search(String value, int firstResult, int maxResults);

    List<Produto> search(long categoria, String value, int firstResult, int maxResults);

    void updatePrices(Promocao promocao);

    void apagar(long codigo);

    void removePromocao(Promocao promocao);

    void decrementStock(long codigo, int quantidade);

}
