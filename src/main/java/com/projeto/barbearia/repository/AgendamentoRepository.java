package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByProfissionalIdAndData(
            Long barbeiroId,
            LocalDate data
    );

    List<Agendamento> findByProfissionalIdAndStatusAgendamento(
            Long barbeiroId,
            StatusAgendamento status
    );

    List<Agendamento> findByProfissionalIdAndDataAndStatusAgendamento(
            Long barbeiroId,
            LocalDate data,
            StatusAgendamento status
    );

    List<Agendamento> findByClienteId(Long clienteId);

    List<Agendamento> findByProfissionalId(Long barbeiroId);

    List<Agendamento> findByClienteIdAndData(
            Long clienteId,
            LocalDate data
    );

    List<Agendamento> findByClienteIdAndStatusAgendamento(
            Long clienteId,
            StatusAgendamento status
    );

    List<Agendamento> findByClienteIdAndDataAndStatusAgendamento(
            Long clienteId,
            LocalDate data,
            StatusAgendamento status
    );

    Boolean existsByDataAndHorarioAndBarbeiroId(
            LocalDate data,
            LocalTime horario,
            Long barbeiroId
    );
}
