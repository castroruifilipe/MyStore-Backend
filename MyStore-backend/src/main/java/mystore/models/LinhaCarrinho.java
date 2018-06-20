package mystore.models;

import java.util.Objects;

public class LinhaCarrinho {

    private Produto produto;

    private int quantidade;


    public LinhaCarrinho() {
    }

    public LinhaCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaCarrinho that = (LinhaCarrinho) o;
        return quantidade == that.quantidade &&
                Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, quantidade);
    }
}
