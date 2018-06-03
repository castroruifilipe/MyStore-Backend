package mystore.models;

import java.io.Serializable;
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
}