package mystore.models;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private double total = 0;

    private List<LinhaCarrinho> linhasCarrinho = new ArrayList<>();


    public Carrinho() {
    }

    public void addProduto(Produto produto, int quantidade) {
        LinhaCarrinho linhaCarrinho = new LinhaCarrinho(produto, quantidade);
        if (linhasCarrinho.contains(linhaCarrinho)) {
            linhaCarrinho = linhasCarrinho.get(linhasCarrinho.indexOf(linhaCarrinho));
            if (linhaCarrinho.getProduto().getPrecoFinal() != produto.getPrecoFinal()) {
                linhaCarrinho.setProduto(produto);
            }
            linhaCarrinho.addQuantidade(quantidade);
        } else {
            linhasCarrinho.add(linhaCarrinho);
        }
        total += produto.getPrecoFinal() * quantidade;
    }

    public void removeProduto(long codigoProduto) {
        Produto produto = new Produto();
        produto.setCodigo(codigoProduto);
        LinhaCarrinho linhaCarrinho = new LinhaCarrinho(produto);
        if (linhasCarrinho.contains(linhaCarrinho)) {
            linhaCarrinho = linhasCarrinho.get(linhasCarrinho.indexOf(linhaCarrinho));
            total -= linhaCarrinho.getSubTotal();
            linhasCarrinho.remove(linhaCarrinho);
        }
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<LinhaCarrinho> getLinhasCarrinho() {
        return linhasCarrinho;
    }

    public void setLinhasCarrinho(List<LinhaCarrinho> linhasCarrinho) {
        this.linhasCarrinho = linhasCarrinho;
    }
}
