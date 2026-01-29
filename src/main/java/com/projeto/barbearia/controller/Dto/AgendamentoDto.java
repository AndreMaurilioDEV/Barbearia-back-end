package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import com.projeto.barbearia.entity.roles.StatusPagamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AgendamentoDto(
        Long id, String nomeBarbeiro, String nomeCliente, LocalDate dataAgendamento,
        LocalTime horaAgendamento, StatusAgendamento statusAgendamento, Double valorTotal, List<ServicoDto> servicoDto ) {
    public static AgendamentoDto fromEntity(Agendamento agendamento) {
        return new AgendamentoDto(
                agendamento.getId(),
                agendamento.getProfissional().getNome(),
                agendamento.getUsuario().getNome(),
                agendamento.getData(),
                agendamento.getHorario(),
                agendamento.getStatusAgendamento(),
                agendamento.getValorTotal(),
                agendamento.getServicoAgendadoList()
                        .stream()
                        .map(sa -> ServicoDto.fromEntity(sa.getServico()))
                        .toList()
        );
    }
}
