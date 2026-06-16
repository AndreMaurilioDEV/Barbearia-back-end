package com.projeto.barbearia.entity.roles;

public enum StatusResgateRecompensa {
    PENDENTE("Pendente"),
    RESGATADO("Resgatado"),
    UTILIZADO("Utilizado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusResgateRecompensa(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
