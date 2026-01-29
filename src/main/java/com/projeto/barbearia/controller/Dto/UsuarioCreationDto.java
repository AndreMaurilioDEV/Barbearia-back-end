package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Usuario;

public record UsuarioCreationDto (String nome, String telefone, String email, String senha) {
    Usuario toEntity() {
        return new Usuario(nome, telefone, email, senha);
    }
}
