package mystore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;

@MappedSuperclass
public class Utilizador implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nome;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String telemovel;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean ativo = true;


    public Utilizador() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return getEmail().equals(that.getEmail());
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
