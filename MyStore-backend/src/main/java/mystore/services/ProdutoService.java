package mystore.services;

import mystore.models.Categoria;
import mystore.models.Produto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProdutoService {

    List<Produto> list();

    void save(Produto produto);

    Produto criar(String nome, String descricao, double precoBase, int stock, Categoria categoria);

    Optional<Produto> get(long codigo);

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos(int quantidadeProdutos);

    List<Produto> maisVendidosDetail(int quantidadeProdutos);

    List<Produto> emPromocao(int quantidadeProdutos);

    List<Produto> porCategoria(long categoria, int pagina, int size);

    List<Produto> related(Produto produto, int size);

    List<Produto> search(String value, int pagina, int size);

    List<Produto> search(long categoria, String value, int pagina, int size);

    void apagar(long codigo);

    Optional<Produto> editar(long codigo, Map<String, String> dados);

}
