package mystore.models;



import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.context.annotation.ScopedProxyMode.INTERFACES;
import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;

@Component
@Scope(value = "session", proxyMode = TARGET_CLASS)
public class Carrinho implements Serializable {

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
        linhasCarrinho = new ArrayList<>();
        total = 0;
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

    @Override
    public String toString() {
        return "Carrinho{" +
                "total=" + total +
                ", linhasCarrinho=" + linhasCarrinho +
                '}';
    }
}
