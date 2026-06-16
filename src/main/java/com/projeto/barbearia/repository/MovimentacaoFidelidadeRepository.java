package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.MovimentacaoFidelidade;
import com.projeto.barbearia.entity.roles.TipoMovimentacaoFidelidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimentacaoFidelidadeRepository extends JpaRepository<MovimentacaoFidelidade, Long> {
    boolean existsByAgendamentoIdAndTipo(Long agendamentoId, TipoMovimentacaoFidelidade tipo);
}
