package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.Profissional;
import com.projeto.barbearia.entity.Usuario;
import com.projeto.barbearia.entity.roles.StatusAgendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoCreationDto(Long clienteId, Long barbeiroId, LocalDate data, LocalTime horario, List<Long> servicosIds) {
    public Agendamento toEntity(Usuario usuario, Profissional profissional) {
        return new Agendamento(usuario, profissional, data, horario);
    }
}
