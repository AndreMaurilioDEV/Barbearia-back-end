package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.Profissional;
import com.projeto.barbearia.entity.ProfissionalDisponibilidade;
import com.projeto.barbearia.entity.roles.DiasSemana;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DisponibilidadeCreationDto(
        @NotNull(message = "Barbeiro é obrigatório")
        Long barbeiroId,

        @NotNull(message = "Hora de início é obrigatória")
        LocalTime horaInicio,

        @NotNull(message = "Hora de fim é obrigatória")
        LocalTime horaFim,

        @NotNull(message = "Dia da semana é obrigatório")
        DiasSemana diasSemana
) {

    @AssertTrue(message = "Hora final deve ser maior que a hora inicial")
    public boolean isHorarioValido() {
        if (horaInicio == null || horaFim == null) return true;
        return horaFim.isAfter(horaInicio);
    }

    public ProfissionalDisponibilidade toEntity(Profissional profissional) {
        return new ProfissionalDisponibilidade(profissional, diasSemana, horaInicio, horaFim);
    }
}
