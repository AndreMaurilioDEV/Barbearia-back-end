package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioCreationDto (

        @NotBlank(message = "Nome não pode ser vazio")
        String nome,

        @NotBlank(message = "Telefone não pode ser vazio") @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido")
        String telefone,

        @NotBlank(message = "Email não pode ser vazio") @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha não pode ser vazia") @Size(min = 8)
        String senha) {
    Usuario toEntity() {
        return new Usuario(nome, telefone, email, senha);
    }
}
