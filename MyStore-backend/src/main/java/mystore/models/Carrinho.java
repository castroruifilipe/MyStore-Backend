package mystore.models;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private double total = 0;

    private List<LinhaCarrinho> linhasCarrinho = new ArrayList<>();


    public Carrinho() {
    }

    public void addProduto(Produto produto, int quantidade) {
        double precoUnitario;
        if (produto.getPrecoPromocional() == 0) {
            precoUnitario = produto.getPrecoBase();
        } else {
            precoUnitario = produto.getPrecoPromocional();
        }

        LinhaCarrinho linhaCarrinho = new LinhaCarrinho(produto.getCodigo(), produto.getNome(), precoUnitario, quantidade);
        if (linhasCarrinho.contains(linhaCarrinho)) {
            linhaCarrinho = linhasCarrinho.get(linhasCarrinho.indexOf(linhaCarrinho));
            if (linhaCarrinho.getPrecoUnitario() != precoUnitario) {
                linhaCarrinho.setPrecoUnitario(precoUnitario);
            }
            linhaCarrinho.addQuantidade(quantidade);
        } else {
            linhasCarrinho.add(linhaCarrinho);
        }
        total += precoUnitario * quantidade;
    }

    public void removeProduto(long codigoProduto) {
        LinhaCarrinho linhaCarrinho = new LinhaCarrinho(codigoProduto);
        if (linhasCarrinho.contains(linhaCarrinho)) {
            total -= linhaCarrinho.getSubTotal();
            linhasCarrinho.remove(linhaCarrinho);
        }
    }
}
