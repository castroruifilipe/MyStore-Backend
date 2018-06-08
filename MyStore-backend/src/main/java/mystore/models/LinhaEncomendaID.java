package mystore.models;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LinhaEncomendaID implements Serializable {


    private Produto produto;
    private Encomenda encomenda;


    public LinhaEncomendaID() {
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinhaEncomendaID that = (LinhaEncomendaID) o;
        return Objects.equals(produto, that.produto) &&
                Objects.equals(encomenda, that.encomenda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, encomenda);
    }
}
