package mystore.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "loja")
public class Loja {

    @Id
    @GeneratedValue
    private long id;

    private String localizacao;

    @Column(nullable = false)
    private String nome;

    @OneToMany
    @JoinColumn(name = "loja")
    private Set<Produto> produtos = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "loja")
    private Set<Funcionario> funcionarios = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "loja")
    private Set<Cliente> clientes = new HashSet<>();


    public Loja() {
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Set<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Set<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        this.clientes = clientes;
    }
}
