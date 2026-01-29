package com.projeto.barbearia.service;


import com.projeto.barbearia.repository.ProfissionalRepository;
import com.projeto.barbearia.repository.UsuarioRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UsuariosService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final ProfissionalRepository profissionalRepository;

    @Autowired
    public UsuariosService(UsuarioRepository usuarioRepository, ProfissionalRepository profissionalRepository) {
        this.usuarioRepository = usuarioRepository;
        this.profissionalRepository = profissionalRepository;

    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        return usuarioRepository.findByEmail(email)
                .map(c -> (UserDetails) c)
                .orElseGet(() ->
                        (UserDetails) profissionalRepository.findByEmail(email)
                                .orElseThrow(() ->
                                        new UsernameNotFoundException("Usuário não encontrado"))
                );
    }
}
