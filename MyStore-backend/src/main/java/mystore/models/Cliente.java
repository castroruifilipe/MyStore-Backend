package mystore.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente extends Utilizador implements Serializable {

    @Column(unique = true)
    private String contribuinte;

    private String morada;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(contribuinte, cliente.contribuinte) &&
                Objects.equals(morada, cliente.morada);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contribuinte, morada, encomendas);
    }
}
