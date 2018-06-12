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

    @Column(name="descricao", unique = true, nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        return getDescricao().equals(categoria.getDescricao());
    }

    @Override
    public int hashCode() {
        return getDescricao().hashCode();
    }
}
