package mystore.models;

import mystore.models.enums.MetodoPagamento;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = true)
    private LocalDate data;

    @Column(nullable = false)
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento", nullable = false)
    private MetodoPagamento metodoPagamento;


    public Pagamento() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return id == pagamento.id &&
                Double.compare(pagamento.total, total) == 0 &&
                Objects.equals(data, pagamento.data) &&
                metodoPagamento == pagamento.metodoPagamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, data, total, metodoPagamento);
    }
}
