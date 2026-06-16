package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.RecompensaFidelidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecompensaFidelidadeRepository extends JpaRepository<RecompensaFidelidade, Long> {
}
