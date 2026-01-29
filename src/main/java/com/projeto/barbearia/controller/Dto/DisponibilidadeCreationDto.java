package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.Profissional;
import com.projeto.barbearia.entity.ProfissionalDisponibilidade;
import com.projeto.barbearia.entity.roles.DiasSemana;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DisponibilidadeCreationDto(Long barbeiroId, LocalTime horaInicio, LocalTime horaFim, DiasSemana diasSemana) {
    public ProfissionalDisponibilidade toEntity(Profissional profissional) {
        return new ProfissionalDisponibilidade(profissional, diasSemana, horaInicio, horaFim);
    }
}
