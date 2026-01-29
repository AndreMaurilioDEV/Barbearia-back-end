package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.Barbeiro;
import com.projeto.barbearia.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<com.projeto.barbearia.entity.Profissional, Long> {
    Optional<Profissional> findByEmail(String email);
}
