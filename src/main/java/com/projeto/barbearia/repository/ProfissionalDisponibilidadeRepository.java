package com.projeto.barbearia.repository;


import com.projeto.barbearia.entity.ProfissionalDisponibilidade;
import com.projeto.barbearia.entity.roles.DiasSemana;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfissionalDisponibilidadeRepository extends JpaRepository<ProfissionalDisponibilidade, Long> {
    Optional<ProfissionalDisponibilidade> findByProfissionalId(Long profissionalId);
    Optional<ProfissionalDisponibilidade> findByDiaDaSemana(DiasSemana diaDaSemana);
}
