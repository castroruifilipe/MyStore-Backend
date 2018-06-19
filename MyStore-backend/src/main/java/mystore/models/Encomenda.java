package mystore.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import mystore.models.enums.EstadoEncomenda;
import mystore.models.enums.MetodoPagamento;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "encomenda")
public class Encomenda {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cliente")
    @JsonBackReference
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEncomenda estado;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "tracking_id", nullable = false)
    private int trackingID;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private float portes;

    @OneToOne
    @JoinColumn(name = "pagamento")
    private Pagamento pagamento;

    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL, mappedBy = "encomenda")
    private Set<LinhaEncomenda> linhasEncomenda;

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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public float getPortes() {
        return portes;
    }

    public void setPortes(float portes) {
        this.portes = portes;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
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
