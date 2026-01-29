package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Agendamento;

import com.projeto.barbearia.entity.Profissional;

public record ProfissionalDto(Long id, String nome, String email, String telefone, Boolean ativo) {
    public static ProfissionalDto fromEntity(Profissional profissional) {
        return new ProfissionalDto(
                profissional.getId(),
                profissional.getNome(),
                profissional.getEmail(),
                profissional.getTelefone(),
                profissional.getAtivo()
        );
    }
}
