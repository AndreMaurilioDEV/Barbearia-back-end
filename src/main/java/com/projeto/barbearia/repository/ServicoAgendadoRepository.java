package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.ServicoAgendado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serial;
import java.util.List;

public interface ServicoAgendadoRepository extends JpaRepository<ServicoAgendado, Long> {
    List<ServicoAgendado> findByAgendamentoBarbeiroId(Long barbeiroId);
}
