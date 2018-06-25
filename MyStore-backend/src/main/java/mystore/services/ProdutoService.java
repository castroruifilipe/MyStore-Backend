package mystore.services;

import mystore.models.Categoria;
import mystore.models.Produto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProdutoService {

    List<Produto> list();

    void save(Produto produto);

    Optional<Produto> get(long codigo);

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos(int quantidadeProdutos);

    Map<Produto, Integer> maisVendidosComQtd(int quantidadeProdutos);

    List<Produto> emPromocao(int quantidadeProdutos);

    List<Produto> porCategoria(long categoria, int pagina, int size);

    List<Produto> related(Produto produto, int size);

    List<Produto> search(String value);

    List<Produto> search(long categoria, String value);

}
