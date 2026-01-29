package com.projeto.barbearia.controller.Dto;


import com.projeto.barbearia.entity.Profissional;

public record ProfissionalCreationDto(String nome, String telefone, String email) {
    Profissional toEntity() {
        return new Profissional(nome, telefone, email);
    }
}
