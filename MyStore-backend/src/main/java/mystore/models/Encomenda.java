package mystore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mystore.models.enums.EstadoEncomenda;
import mystore.models.enums.MetodoPagamento;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static mystore.models.enums.EstadoEncomenda.AGUARDA_PAGAMENTO;

@Entity
@Table(name = "encomenda")
public class Encomenda {

    @Id
    @GeneratedValue
    private long id;

    @JsonIgnoreProperties("encomendas")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEncomenda estado;

    @Column(name = "data_registo", nullable = false)
    private LocalDateTime dataRegisto;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "morada_entrega")
    private Morada moradaEntrega;

    @Column(nullable = false)
    private double portes;

    @JsonIgnoreProperties("encomenda")
    @OneToMany(fetch = EAGER, cascade = ALL, mappedBy = "encomenda")
    private Set<LinhaEncomenda> linhasEncomenda;

    private double total;

    @Column(name = "data_limite_pagamento")
    private LocalDate dataLimitePagamento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;


    public Encomenda() {
    }

    public long getId() {
        return id;
    }

    public EstadoEncomenda getEstado() {
        return estado;
    }

    public void setEstado(EstadoEncomenda estado) {
        this.estado = estado;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public Morada getMoradaEntrega() {
        return moradaEntrega;
    }

    public void setMoradaEntrega(Morada moradaEntrega) {
        this.moradaEntrega = moradaEntrega;
    }

    public double getPortes() {
        return portes;
    }

    public void setPortes(double portes) {
        this.portes = portes;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Set<LinhaEncomenda> getLinhasEncomenda() {
        return linhasEncomenda;
    }

    public void setLinhasEncomenda(Set<LinhaEncomenda> linhasEncomenda) {
        this.linhasEncomenda = linhasEncomenda;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public LocalDate getDataLimitePagamento() {
        return dataLimitePagamento;
    }

    public void setDataLimitePagamento(LocalDate dataLimitePagamento) {
        this.dataLimitePagamento = dataLimitePagamento;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @PrePersist
    public void setDefault() {
        if (dataRegisto == null) {
            dataRegisto = LocalDateTime.now();
        }
        if (dataLimitePagamento == null) {
            dataLimitePagamento = LocalDate.now().plusDays(7);
        }
        if (portes == 0.0) {
            portes = 5.45;
        }
        if (total == 0.0) {
            total = portes + linhasEncomenda
                    .parallelStream()
                    .mapToDouble(LinhaEncomenda::getSubTotal)
                    .sum();
        }
        if (estado == null) {
            estado = AGUARDA_PAGAMENTO;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encomenda encomenda = (Encomenda) o;
        return id == encomenda.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
