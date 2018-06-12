package mystore.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue
    private long codigo;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(name = "preco_base", nullable = false)
    private double precoBase;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private float iva;

    @Column(name = "data_registo")
    private LocalDateTime dataRegisto;

    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "produto_promocao",
            joinColumns = @JoinColumn(name = "produto"),
            inverseJoinColumns = @JoinColumn(name = "promocao")
    )
    private Set<Promocao> promocoes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "produto")
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

    public Set<Promocao> getPromocoes() {
        return promocoes;
    }

    public void setPromocoes(Set<Promocao> promocoes) {
        this.promocoes = promocoes;
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

    @PrePersist
    public void setDataRegistoDefault() {
        if (this.getDataRegisto() == null) {
            this.setDataRegisto(LocalDateTime.now());
        }
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
