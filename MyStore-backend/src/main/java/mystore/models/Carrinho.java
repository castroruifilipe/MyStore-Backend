package mystore.models;

import java.util.HashSet;
import java.util.Set;

public class Carrinho {

    private long id;

    private Cliente cliente;

    private Set<LinhaCarrinho> linhasCarrinho = new HashSet<>();


    public Carrinho() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<LinhaCarrinho> getLinhasCarrinho() {
        return linhasCarrinho;
    }

    public void setLinhasCarrinho(Set<LinhaCarrinho> linhasCarrinho) {
        this.linhasCarrinho = linhasCarrinho;
    }

    public void addProduto(Produto produto, int quantidade) {
        linhasCarrinho.add(new LinhaCarrinho(produto, quantidade));
    }

    public void removeProduto(long codigo) {
        for (LinhaCarrinho linha : linhasCarrinho) {
            if (linha.getProduto().getCodigo() == codigo) {
                linhasCarrinho.remove(linha);
                return;
            }
        }
    }
}
