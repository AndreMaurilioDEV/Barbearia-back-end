package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Servico;
import com.projeto.barbearia.entity.roles.TiposServicos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServicoCreationDto(

        @NotNull(message = "Tipo de serviço é obrigatório")
        TiposServicos tiposServicos,

        @NotBlank(message = "Descrição não pode ser vazia")
        String descricao,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser maior que zero")
        Double preco,

        @NotNull(message = "Duração é obrigatória")
        @Positive(message = "Duração deve ser maior que zero")
        Integer duracaoMinutos

) {
    Servico toEntity() {
        return new Servico(tiposServicos, descricao, preco, duracaoMinutos);
    }
}
