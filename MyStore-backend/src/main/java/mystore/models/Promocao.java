package mystore.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "promocao")
public class Promocao implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private double desconto;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @ManyToMany(mappedBy = "promocoes", fetch = FetchType.EAGER)
    private Set<Produto> produtos = new HashSet<>();


    public Promocao() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promocao promocao = (Promocao) o;
        return id == promocao.id &&
                Double.compare(promocao.desconto, desconto) == 0 &&
                Objects.equals(descricao, promocao.descricao) &&
                Objects.equals(dataInicio, promocao.dataInicio) &&
                Objects.equals(dataFim, promocao.dataFim) &&
                Objects.equals(produtos, promocao.produtos);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, desconto, descricao, dataInicio, dataFim, produtos);
    }
}
