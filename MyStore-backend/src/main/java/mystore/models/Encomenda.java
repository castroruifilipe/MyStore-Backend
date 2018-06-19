package mystore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import mystore.models.enums.EstadoEncomenda;
import mystore.models.enums.MetodoPagamento;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

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

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "tracking_id", nullable = false)
    private int trackingID;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "morada_envio")
    private Morada moradaEnvio;

    @OneToOne(fetch = EAGER, cascade = ALL)
    @JoinColumn(name = "morada_faturacao")
    private Morada moradaFaturacao;

    @Column(nullable = false)
    private double portes;

    @JsonIgnoreProperties("encomenda")
    @OneToMany(fetch = EAGER, cascade = ALL, mappedBy = "encomenda")
    private Set<LinhaEncomenda> linhasEncomenda;

    private double total;

    @Column
    private LocalDate dataPagamento;

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(int trackingID) {
        this.trackingID = trackingID;
    }

    public Morada getMoradaEnvio() {
        return moradaEnvio;
    }

    public void setMoradaEnvio(Morada moradaEnvio) {
        this.moradaEnvio = moradaEnvio;
    }

    public Morada getMoradaFaturacao() {
        return moradaFaturacao;
    }

    public void setMoradaFaturacao(Morada moradaFaturacao) {
        this.moradaFaturacao = moradaFaturacao;
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

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Encomenda encomenda = (Encomenda) o;
        return getTrackingID() == encomenda.getTrackingID();
    }

    @Override
    public int hashCode() {
        return getTrackingID();
    }
}
