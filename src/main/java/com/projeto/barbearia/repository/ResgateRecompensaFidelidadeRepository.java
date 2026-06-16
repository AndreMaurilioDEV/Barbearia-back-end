package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.ResgateRecompensaFidelidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResgateRecompensaFidelidadeRepository extends JpaRepository<ResgateRecompensaFidelidade, Long> {
}
