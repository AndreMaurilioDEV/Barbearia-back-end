package com.projeto.barbearia.controller.Dto;


import com.projeto.barbearia.entity.Profissional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ProfissionalCreationDto(

        @NotBlank(message = "Nome não pode ser vazio")
        String nome,

        @NotBlank(message = "Telefone não pode ser vazio") @Pattern(regexp = "^\\d{10,11}$", message = "Telefone inválido")
        String telefone,

        @Email(message = "Email inválido") @NotBlank(message = "Email não pode ser vazio")
        String email) {

    Profissional toEntity() {
        return new Profissional(nome, telefone, email);
    }
}
