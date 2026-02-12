package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.Profissional;
import com.projeto.barbearia.entity.Usuario;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoCreationDto(

        @NotNull(message = "Cliente é obrigatório")
        Long clienteId,

        @NotNull(message = "Barbeiro é obrigatório")
        Long barbeiroId,

        @NotNull(message = "Data é obrigatória")
        @FutureOrPresent(message = "Data não pode ser no passado")
        LocalDate data,

        @NotNull(message = "Horário é obrigatório")
        LocalTime horario,

        @NotEmpty(message = "Deve conter ao menos um serviço")
        List<@NotNull Long> servicosIds

) {
    public Agendamento toEntity(Usuario usuario, Profissional profissional) {
        return new Agendamento(usuario, profissional, data, horario);
    }
}
