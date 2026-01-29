package com.projeto.barbearia.entity.roles;

public enum FormaPagamento {
    PRESENCIAL("Presencial"),
    ONLINE("Online");

    private final String descricao;

    FormaPagamento(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
