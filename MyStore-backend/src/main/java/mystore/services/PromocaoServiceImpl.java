package mystore.services;

import mystore.daos.ProdutoDAO;
import mystore.daos.PromocaoDAO;
import mystore.models.Produto;
import mystore.models.Promocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.AbstractMap.SimpleEntry;


@Service
@Transactional
public class PromocaoServiceImpl implements PromocaoService {

    @Autowired
    private PromocaoDAO promocaoDAO;

    @Autowired
    private ProdutoDAO produtoDAO;


    @Override
    @Transactional
    public void save(Promocao promocao) {
        promocaoDAO.save(promocao);
        if (promocao.isAtual()) {
            produtoDAO.updatePrices(promocao);
        }
    }

    @Override
    public Optional<SimpleEntry<Promocao, Double>> get(Produto produto) {
        List<Promocao> promocoesProduto = promocaoDAO.listByProduto(produto.getCodigo());
        List<Promocao> promocoesCategoria = promocaoDAO.listByCategoria(produto.getCategoria().getDescricao());

        List<Promocao> promocoes = new ArrayList<>();
        promocoes.addAll(promocoesProduto);
        promocoes.addAll(promocoesCategoria);

        double melhor_preco = produto.getPrecoBase();
        Promocao melhor_promocao = null;
        for (Promocao promocao : promocoes) {
            double preco = promocao.getPrecoFinal(produto.getPrecoBase());
            if (preco < melhor_preco) {
                melhor_preco = preco;
                melhor_promocao = promocao;
            }
        }
        if (melhor_promocao == null) {
            return Optional.empty();
        } else {
            return Optional.of(new SimpleEntry<>(melhor_promocao, melhor_preco));
        }
    }
}
