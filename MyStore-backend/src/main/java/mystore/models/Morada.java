package mystore.models;

import javax.persistence.*;

@Entity
@Table(name = "morada")
public class Morada {

    @Id
    @GeneratedValue
    private long id;

    private String rua;

    private String localidade;

    @Column(name = "codigo_postal")
    private String codigoPostal;


    public Morada() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
