package com.projeto.barbearia.entity;


import com.projeto.barbearia.entity.roles.DiasSemana;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "profissional_disponibilidade")
public class ProfissionalDisponibilidade {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profissional_id", nullable = false)
    private Profissional profissional;

    private DiasSemana diaDaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public ProfissionalDisponibilidade() {}

    public ProfissionalDisponibilidade(Profissional profissional, DiasSemana diaDaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.profissional = profissional;
        this.diaDaSemana = diaDaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public DiasSemana getDia_da_semana() {
        return diaDaSemana;
    }

    public void setDia_da_semana(DiasSemana diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public LocalTime getHora_inicio() {
        return horaInicio;
    }

    public void setHora_inicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHora_fim() {
        return horaFim;
    }

    public void setHora_fim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }
}
