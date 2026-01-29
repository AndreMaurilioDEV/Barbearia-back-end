package com.projeto.barbearia.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "servicos_agendados")
public class ServicoAgendado {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agendamento_id", nullable = false)
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    private ServicoAgendado() {}

    public ServicoAgendado(Agendamento agendamento, Servico servico) {
        this.agendamento = agendamento;
        this.servico = servico;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
