package mystore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "cliente")
public class Cliente extends Utilizador implements Serializable {

    @Column(unique = true)
    private String contribuinte;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "morada")
    private Morada morada = new Morada();

    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "cliente")
    @JsonIgnoreProperties("cliente")
    private Set<Encomenda> encomendas = new HashSet();


    public Cliente() {
    }

    public String getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(String contribuinte) {
        this.contribuinte = contribuinte;
    }

    public Set<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void setEncomendas(Set<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }

    public Morada getMorada() {
        return morada;
    }

    public void setMorada(Morada morada) {
        this.morada = morada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(contribuinte, cliente.contribuinte);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contribuinte, morada, encomendas);
    }
}
