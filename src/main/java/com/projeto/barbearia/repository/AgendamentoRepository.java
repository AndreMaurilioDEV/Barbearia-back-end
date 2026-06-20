package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    List<Agendamento> findByUsuarioId(Long usuarioId);

    List<Agendamento> findByProfissionalId(Long barbeiroId);

    List<Agendamento> findByUsuarioIdAndData(
            Long usuarioId,
            LocalDate data
    );

    List<Agendamento> findByUsuarioIdAndStatusAgendamento(
            Long usuarioId,
            StatusAgendamento status
    );

    List<Agendamento> findByUsuarioIdAndDataAndStatusAgendamento(
            Long usuarioId,
            LocalDate data,
            StatusAgendamento status
    );

    Boolean existsByDataAndHorarioAndProfissionalId(
            LocalDate data,
            LocalTime horario,
            Long profissionalId
    );

    List<Agendamento> findByDataAndHorarioBetweenAndStatusAgendamento(
            LocalDate data,
            LocalTime localTime,
            LocalTime localTime1,
            StatusAgendamento statusAgendamento
    );

    List<Agendamento> findByProfissionalIdAndDataAndStatusAgendamentoOrderByPosicaoFilaAsc(
            Long profissionalId,
            LocalDate data,
            StatusAgendamento statusAgendamento
    );

    Optional<Agendamento> findFirstByProfissionalIdAndDataAndStatusAgendamento(
            Long profissionalId,
            LocalDate data,
            StatusAgendamento statusAgendamento
    );


}
