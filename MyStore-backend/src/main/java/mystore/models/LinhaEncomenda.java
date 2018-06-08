package mystore.models;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "linha_encomenda")
@AssociationOverrides({
        @AssociationOverride(name = "id.produto", joinColumns = @JoinColumn(name = "produto")),
        @AssociationOverride(name = "id.encomenda", joinColumns = @JoinColumn(name = "encomenda"))
})
public class LinhaEncomenda implements Serializable {

    private LinhaEncomendaID id = new LinhaEncomendaID();

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private float preco;

    @Column(nullable = false)
    private float valorDesconto;


    public LinhaEncomenda() {
    }

    @EmbeddedId
    public LinhaEncomendaID getId() {
        return id;
    }

    public void setId(LinhaEncomendaID id) {
        this.id = id;
    }

    @Transient
    public Produto getProduto() {
        return getId().getProduto();
    }

    public void setProduto(Produto produto) {
        getId().setProduto(produto);
    }

    @Transient
    public Encomenda getEncomenda() {
        return getId().getEncomenda();
    }

    public void setEncomenda(Encomenda encomenda) {
        getId().setEncomenda(encomenda);
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public float getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(float valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

}
