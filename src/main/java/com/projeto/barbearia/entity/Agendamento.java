package com.projeto.barbearia.entity;


import com.projeto.barbearia.entity.roles.StatusAgendamento;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agendamentos")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAgendado> servicoAgendadoList = new ArrayList<>();

    @OneToMany(mappedBy = "agendamento")
    private List<Pagamento> pagamentos;

    private LocalDate data;

    private Double valorTotal;

    private LocalTime horario;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento statusAgendamento = StatusAgendamento.PENDENTE;

    public Agendamento () {}

    public Agendamento(Usuario usuario, Profissional profissional, LocalDate data, LocalTime horario) {
        this.usuario = usuario;
        this.profissional = profissional;
        this.data = data;
        this.horario = horario;
        this.servicoAgendadoList = new ArrayList<>();
    }


    public Long getId() {
        return id;
    }

    public List<ServicoAgendado> getServicoAgendadoList() {
        return servicoAgendadoList;
    }

    public void setServicoAgendadoList(List<ServicoAgendado> servicoAgendadoList) {
        this.servicoAgendadoList = servicoAgendadoList;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }



    public StatusAgendamento getStatusAgendamento() {
        return statusAgendamento;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
}
