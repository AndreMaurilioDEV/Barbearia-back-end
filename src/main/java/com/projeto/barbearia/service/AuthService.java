package com.projeto.barbearia.service;


import com.projeto.barbearia.entity.Usuario;

import com.projeto.barbearia.repository.UsuarioRepository;
import com.projeto.barbearia.security.Role;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService  extends DefaultOAuth2UserService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        System.out.println("OAuth iniciado");
        OAuth2User user = super.loadUser(request);

        String googleId = user.getAttribute("sub");
        String email = user.getAttribute("email");
        String nome = user.getAttribute("name");

        usuarioRepository.findByEmail(email)
                .orElseGet(() -> criarClienteGoogle(googleId, email, nome));
        System.out.println(">>> Cliente salvo no banco");
        return user;
    }

    public Usuario criarClienteGoogle(String googleId, String email, String nome) {
        Usuario usuario = new Usuario();
        usuario.setGoogle_id(googleId);
        usuario.setEmail(email);
        usuario.setNome(nome);
        usuario.setPassword(null);
        usuario.setRole(Role.CLIENTE);
        usuario.setCreated_at(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }
}
