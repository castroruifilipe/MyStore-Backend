package mystore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

public class LinhaCarrinho implements Serializable {

    @JsonIgnoreProperties(value = {"descricao", "dataRegisto", "categoria", "linhasEncomenda"})
    private Produto produto;

    private int quantidade;

    private double subTotalSemDesc;

    private double subTotal;


    public LinhaCarrinho() {
    }

    public LinhaCarrinho(Produto produto) {
        this.produto = produto;
    }

    public LinhaCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.subTotalSemDesc = this.quantidade * produto.getPrecoBase();
        this.subTotal = this.quantidade * produto.getPrecoFinal();
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getSubTotalSemDesc() {
        return subTotalSemDesc;
    }

    public void setSubTotalSemDesc(double subTotalSemDesc) {
        this.subTotalSemDesc = subTotalSemDesc;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.subTotalSemDesc = produto.getPrecoBase() * this.quantidade;
        this.subTotal = produto.getPrecoFinal() * this.quantidade;
    }

    public void addQuantidade(int quantidade) {
        this.quantidade += quantidade;
        this.subTotalSemDesc = produto.getPrecoBase() * this.quantidade;
        this.subTotal = produto.getPrecoFinal() * this.quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaCarrinho that = (LinhaCarrinho) o;
        return produto.getCodigo() == that.getProduto().getCodigo();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(produto.getCodigo());
    }
}
