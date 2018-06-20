package mystore.services;

import mystore.models.Categoria;
import mystore.models.Produto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    List<Produto> list();

    void save(Produto produto);

    Optional<Produto> get(long codigo);

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos(int quantidadeProdutos);

    List<Produto> porCategoria(long categoria, int pagina, int size);

    List<Produto> listPromocao();

    List<Produto> search(String value);

    List<Produto> search(long categoria, String value);
}
