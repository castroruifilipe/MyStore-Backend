package mystore.models;

public class LinhaCarrinho {

    private long codigoProduto;

    private String nomeProduto;

    private int quantidade;

    private double precoUnitario;

    private double subTotal;


    public LinhaCarrinho(long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public LinhaCarrinho(long codigoProduto, String nomeProduto, double precoUnitario, int quantidade) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subTotal = this.quantidade * this.precoUnitario;
    }

    public long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.subTotal = this.precoUnitario * this.quantidade;
    }

    public void addQuantidade(int quantidade) {
        this.quantidade += quantidade;
        this.subTotal = this.precoUnitario * this.quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaCarrinho that = (LinhaCarrinho) o;
        return codigoProduto == that.codigoProduto;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(codigoProduto);
    }
}
