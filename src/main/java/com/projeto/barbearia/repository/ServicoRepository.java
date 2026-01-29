package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.Servico;
import com.projeto.barbearia.entity.roles.TiposServicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Optional<Servico> findByTiposServicos(TiposServicos tiposServicos);
    boolean existsByTiposServicos(TiposServicos tiposServicos);

}

