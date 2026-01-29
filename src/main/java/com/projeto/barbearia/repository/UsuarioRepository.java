package com.projeto.barbearia.repository;

import com.projeto.barbearia.entity.Cliente;
import com.projeto.barbearia.entity.Usuario;
import com.projeto.barbearia.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByRole(Role role);
    Optional<String> findByUsername(String adminEmail);
}
