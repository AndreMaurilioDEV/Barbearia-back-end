package com.projeto.barbearia.entity.roles;

public enum StatusPagamento {
    PENDENTE("Pendente"),
    PAGO("Pago"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
