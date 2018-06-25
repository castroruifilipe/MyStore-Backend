package mystore.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "linha_encomenda")
public class LinhaEncomenda implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "produto")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "encomenda")
    private Encomenda encomenda;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "preco_unitario", nullable = false)
    private double precoUnitario;

    @Column(nullable = false)
    private double valorDesconto;


    public LinhaEncomenda() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double preco) {
        this.precoUnitario = preco;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public double getSubTotal() {
        return (precoUnitario - valorDesconto) * quantidade;
    }

    @PrePersist
    public void setDefault() {
        if (precoUnitario == 0.0) {
            precoUnitario = produto.getPrecoBase();
        }
        if (valorDesconto == 0.0) {
            valorDesconto = produto.getValorDesconto();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaEncomenda that = (LinhaEncomenda) o;
        return Objects.equals(produto, that.produto) &&
                Objects.equals(encomenda, that.encomenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, produto, encomenda, quantidade, precoUnitario, valorDesconto);
    }
}
