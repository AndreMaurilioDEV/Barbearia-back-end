package com.projeto.barbearia.entity;

import com.projeto.barbearia.entity.roles.MetodoPagamento;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import com.projeto.barbearia.entity.roles.StatusPagamento;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento;

    private Double valor;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento statusPagamento;

    private LocalDateTime pagoEm;

    public Pagamento(Agendamento agendamento, Double valor, MetodoPagamento metodoPagamento, StatusPagamento statusPagamento, LocalDateTime pagoEm) {
        this.agendamento = agendamento;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
        this.statusPagamento = statusPagamento;
        this.pagoEm = pagoEm;
    }

    private Pagamento() {}

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public LocalDateTime getPagoEm() {
        return pagoEm;
    }

    public void setPagoEm(LocalDateTime pagoEm) {
        this.pagoEm = pagoEm;
    }
}
