package com.projeto.barbearia.controller.Dto;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.Servico;
import com.projeto.barbearia.entity.roles.TiposServicos;

public record ServicoDto (
    Long id,
    TiposServicos tiposServicos,
    String descricao,
    Integer duracaoMinutos,
    Double preco
) {
    public static ServicoDto fromEntity(Servico servico) {
        return new ServicoDto(
                servico.getId(),
                servico.getTiposServicos(),
                servico.getDescricao(),
                servico.getDuracaoMinutos(),
                servico.getPreco()
        );
    }
}
