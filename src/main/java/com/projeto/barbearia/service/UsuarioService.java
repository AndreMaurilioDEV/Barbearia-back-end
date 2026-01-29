package com.projeto.barbearia.service;


import com.projeto.barbearia.controller.Dto.UsuarioCreationDto;

import com.projeto.barbearia.entity.Usuario;

import com.projeto.barbearia.repository.UsuarioRepository;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioEmailException;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNaoEncontrado;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNomeException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService   {
    private final UsuarioRepository usuarioRepository;


    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }


    public Usuario findById(Long id) throws UsuarioNaoEncontrado {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontrado("Cliente não encontrado"));
    }

    public Usuario findByEmail(String email) throws UsuarioNaoEncontrado {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNaoEncontrado("Cliente não encontrado"));
    }

    @Transactional
    public Usuario createUsuario(UsuarioCreationDto usuarioCreationDto) throws UsuarioEmailException, UsuarioNomeException {
        if (usuarioRepository.findByEmail(usuarioCreationDto.email()).isPresent()) {
            throw new UsuarioEmailException("Cliente com este email ja existe");
        }

        if (usuarioCreationDto.nome() == null || usuarioCreationDto.nome().isEmpty()) {
            throw new UsuarioNomeException("Nome não pode ser vazio");
        }

        if (usuarioCreationDto.email() == null || usuarioCreationDto.email().isEmpty()) {
            throw new UsuarioEmailException("Email não pode ser vazio");
        }

        String hashedPassword = new BCryptPasswordEncoder()
                .encode(usuarioCreationDto.senha());

        Usuario usuario = new Usuario(
                usuarioCreationDto.nome(),
                usuarioCreationDto.telefone(),
                usuarioCreationDto.email(),
                hashedPassword
        );
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUsuario(UsuarioCreationDto usuarioCreationDto, Long id) throws UsuarioNaoEncontrado {
       Usuario usuarioFromDb =  usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontrado("Cliente não encontrado"));
        usuarioFromDb.setNome(usuarioCreationDto.nome());
        usuarioFromDb.setTelefone(usuarioCreationDto.telefone());
        return usuarioRepository.save(usuarioFromDb);
    }

}
