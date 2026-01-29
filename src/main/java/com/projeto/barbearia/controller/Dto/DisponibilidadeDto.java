package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.ProfissionalDisponibilidade;
import com.projeto.barbearia.entity.roles.DiasSemana;

import java.time.LocalTime;

public record DisponibilidadeDto(Long id, DiasSemana diaDaSemana, LocalTime horarioInicio, LocalTime horarioFim) {
    public static DisponibilidadeDto fromEntity(ProfissionalDisponibilidade profissionalDisponibilidade) {
        return new DisponibilidadeDto(
                profissionalDisponibilidade.getProfissional().getId(),
                profissionalDisponibilidade.getDia_da_semana(),
                profissionalDisponibilidade.getHora_inicio(),
                profissionalDisponibilidade.getHora_fim()
        );

    }
}
