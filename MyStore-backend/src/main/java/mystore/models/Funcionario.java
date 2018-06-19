package mystore.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "funcionario")
public class Funcionario extends Utilizador implements Serializable {

    @Column(unique = true)
    private long numero;


    public Funcionario() {
    }

    public void setNumero(long value) {
        this.numero = value;
    }

    public long getNumero() {
        return numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Funcionario that = (Funcionario) o;
        return numero == that.numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numero);
    }
}