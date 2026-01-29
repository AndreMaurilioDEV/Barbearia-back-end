package com.projeto.barbearia.service;


import com.projeto.barbearia.entity.Usuario;

import com.projeto.barbearia.repository.UsuarioRepository;
import com.projeto.barbearia.security.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Value("${ADMIN_NAME}")
    private String adminName;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void createAdmin() {
        if (usuarioRepository.findByUsername(adminEmail).isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setNome(adminName);
            usuario.setEmail(adminEmail);
            usuario.setPassword(passwordEncoder.encode(adminPassword));
            usuario.setRole(Role.ADMIN);
            usuarioRepository.save(usuario);
            System.out.println("Usuário Admin criado com sucesso!");
        }
    }
}
