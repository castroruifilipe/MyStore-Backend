package mystore.services;

import mystore.daos.ProdutoDAO;
import mystore.models.Categoria;
import mystore.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    @Autowired
    protected ProdutoDAO produtoDAO;


    @Override
    @Transactional
    public List<Produto> list() {
        return produtoDAO.getAll();
    }

    @Override
    public Optional<Produto> get(long codigo) {
        return produtoDAO.find(codigo);
    }

    @Override
    public List<Produto> novidades(int quantidadeProdutos) {
        return produtoDAO.novidades(quantidadeProdutos);
    }

    @Override
    public List<Produto> maisVendidos(int quantidadeProdutos) {
        return produtoDAO.maisVendidos(quantidadeProdutos);
    }

    @Override
    public List<Produto> porCategoria(long categoria, int pagina, int size) {
        return produtoDAO.listPaginated(categoria, (pagina - 1) * size, size);
    }

    @Override
    public List<Produto> listPromocao() {
        return null;
    }

    @Override
    public void save(Produto produto) {
        produtoDAO.save(produto);
    }
}
