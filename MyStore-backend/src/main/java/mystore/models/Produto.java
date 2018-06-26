package mystore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Entity
@Table(name = "produto")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Produto implements Serializable {

    @Id
    @GeneratedValue
    private long codigo;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "preco_base", nullable = false)
    private double precoBase;

    @Column(name = "preco_promocional", nullable = false)
    private double precoPromocional;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private float iva;

    @Column(name = "data_registo")
    private LocalDateTime dataRegisto;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    @JsonIgnoreProperties("produto")
    @OneToOne(mappedBy = "produto", cascade = ALL, fetch = LAZY)
    private EstatisticasVendas estatisticasVendas;

    @JsonIgnoreProperties("produto")
    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "produto")
    private Set<LinhaEncomenda> linhasEncomenda = new HashSet<>();


    public Produto() {
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(double precoBase) {
        this.precoBase = precoBase;
    }

    public double getPrecoPromocional() {
        return precoPromocional;
    }

    public void setPrecoPromocional(double precoPromocional) {
        this.precoPromocional = precoPromocional;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<LinhaEncomenda> getLinhasEncomenda() {
        return linhasEncomenda;
    }

    public void setLinhasEncomenda(Set<LinhaEncomenda> linhasEncomenda) {
        this.linhasEncomenda = linhasEncomenda;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public double getPrecoFinal() {
        return precoPromocional != 0 ? precoPromocional : precoBase;
    }

    public double getValorDesconto() {
        return precoBase - getPrecoFinal();
    }

    public EstatisticasVendas getEstatisticasVendas() {
        return estatisticasVendas;
    }

    public void setEstatisticasVendas(EstatisticasVendas estatisticasVendas) {
        this.estatisticasVendas = estatisticasVendas;
    }

    @PrePersist
    public void setDefault() {
        dataRegisto = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return getNome().equals(produto.getNome());
    }

    @Override
    public int hashCode() {
        return getNome().hashCode();
    }
}
