package mystore.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue
    private long id;

    private String descricao;

    @OneToMany
    @JoinColumn(name = "categoria")
    private Set<Promocao> promocoes = new HashSet<>();


    public Categoria() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Promocao> getPromocoes() {
        return promocoes;
    }

    public void setPromocoes(Set<Promocao> promocoes) {
        this.promocoes = promocoes;
    }
}
