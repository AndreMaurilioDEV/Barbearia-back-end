package com.projeto.barbearia.controller.Dto;


import com.projeto.barbearia.entity.Usuario;

public record UsuarioDto (Long id, String nome, String telefone, String email) {
    public static UsuarioDto fromEntity(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getEmail()
        );
    }
}
