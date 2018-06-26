package mystore.models;

import javax.persistence.*;

@Entity
@Table(name = "estatisticas_vendas")
public class EstatisticasVendas {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "numero_vendas")
    private int numeroVendas;

    @Column(name = "numero_vendas_promocao")
    private int numeroVendasPromocao;

    @Column(name = "numero_encomendas")
    private int numeroEncomendas;

    @Column(name = "total_faturado")
    private double totalFaturado;


    public EstatisticasVendas() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumeroVendas() {
        return numeroVendas;
    }

    public void setNumeroVendas(int numeroVendas) {
        this.numeroVendas = numeroVendas;
    }

    public int getNumeroVendasPromocao() {
        return numeroVendasPromocao;
    }

    public void setNumeroVendasPromocao(int numeroVendasPromocao) {
        this.numeroVendasPromocao = numeroVendasPromocao;
    }

    public int getNumeroEncomendas() {
        return numeroEncomendas;
    }

    public void setNumeroEncomendas(int numeroEncomendas) {
        this.numeroEncomendas = numeroEncomendas;
    }

    public double getTotalFaturado() {
        return totalFaturado;
    }

    public void setTotalFaturado(double totalFaturado) {
        this.totalFaturado = totalFaturado;
    }
}
