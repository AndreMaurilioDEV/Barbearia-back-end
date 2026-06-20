package com.projeto.barbearia.entity.roles;

public enum OrigemEntrada {
    FILA_PRESENCIAL("Presencial"),
    FILA_ONLINE("Online"),
    AGENDAMENTO("Agendamento");

    private final String descricao;

    OrigemEntrada(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
