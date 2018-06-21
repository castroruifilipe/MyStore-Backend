package mystore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class LinhaCarrinho {

    @JsonIgnoreProperties(value = {"descricao", "stock", "iva", "dataRegisto", "categoria", "linhasEncomenda"})
    private Produto produto;

    //private long codigoProduto;

    //private String nomeProduto;

    private int quantidade;

    //private double precoUnitario;

    private double subTotal;


    /*public LinhaCarrinho(long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }*/

    public LinhaCarrinho(Produto produto) {
        this.produto = produto;
    }

    /*public LinhaCarrinho(long codigoProduto, String nomeProduto, double precoUnitario, int quantidade) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subTotal = this.quantidade * this.precoUnitario;
    }*/

    public LinhaCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
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

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.subTotal = produto.getPrecoFinal() * this.quantidade;
    }

    public void addQuantidade(int quantidade) {
        this.quantidade += quantidade;
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
