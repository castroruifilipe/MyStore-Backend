package mystore.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente extends Utilizador implements Serializable {


    @Column(unique = true)
    private String contribuinte;

    private String morada;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente")
    private Set<Encomenda> encomendas = new HashSet();


    public Cliente() {
    }

    public String getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public Set<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(Set<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }

}
