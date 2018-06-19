package mystore.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "cliente")
public class Cliente extends Utilizador implements Serializable {

    @Column(unique = true)
    private String contribuinte;

    private String rua;

    private String localidade;

    private String codigoPostal;

    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "cliente")
    @JsonManagedReference
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

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(contribuinte, cliente.contribuinte) &&
                Objects.equals(rua, cliente.rua) &&
                Objects.equals(localidade, cliente.localidade) &&
                Objects.equals(codigoPostal, cliente.codigoPostal) &&
                Objects.equals(encomendas, cliente.encomendas);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), contribuinte, rua, localidade, codigoPostal, encomendas);
    }
}
