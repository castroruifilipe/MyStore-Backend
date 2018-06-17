package mystore.services;

import mystore.models.Categoria;
import mystore.models.Produto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProdutoService extends GenericService<Produto> {

    List<Produto> list();

    List<Produto> novidades(int quantidadeProdutos);

    List<Produto> maisVendidos(int quantidadeProdutos);

    List<Produto> porCategoria(long categoria, int pagina, int size);

    List<Produto> listPromocao();
}
