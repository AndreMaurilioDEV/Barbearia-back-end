package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Servico;
import com.projeto.barbearia.entity.roles.TiposServicos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServicoCreationDto(   @NotNull TiposServicos tiposServicos,
                                     @NotBlank String descricao,
                                     @NotNull @Positive Double preco,
                                     @NotNull @Positive Integer duracaoMinutos) {
    Servico toEntity() {
        return new Servico(tiposServicos, descricao, preco, duracaoMinutos);
    }
}
