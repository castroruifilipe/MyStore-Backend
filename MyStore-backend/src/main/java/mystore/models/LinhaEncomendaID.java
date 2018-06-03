package mystore.models;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

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
}
