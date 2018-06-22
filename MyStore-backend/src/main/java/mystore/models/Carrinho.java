package mystore.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public void update(Map<Long, Integer> quantidades) {
        Produto produto = new Produto();
        LinhaCarrinho linhaCarrinho = new LinhaCarrinho();

        for (Map.Entry<Long, Integer> entry : quantidades.entrySet()) {
            long codigoProduto = entry.getKey();
            int quantidade = entry.getValue();
            if (quantidade == 0) {
                removeProduto(codigoProduto);
            } else {
                produto.setCodigo(codigoProduto);
                linhaCarrinho.setProduto(produto);
                if (linhasCarrinho.contains(linhaCarrinho)) {
                    linhaCarrinho = linhasCarrinho.get(linhasCarrinho.indexOf(linhaCarrinho));
                    linhaCarrinho.setQuantidade(quantidade);
                }
            }
        }
        total = 0;
        for (LinhaCarrinho linha : linhasCarrinho) {
            total += linha.getSubTotal();
        }
    }

    public void clear() {
        this.linhasCarrinho.clear();
        this.total = 0;
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
