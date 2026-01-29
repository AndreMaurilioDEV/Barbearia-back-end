package com.projeto.barbearia.entity.roles;

public enum TiposServicos {
    CORTE("Corte"),
    BARBA("Barba"),
    Sobrancelha("Sobrancelha"),
    LAVAGEM("Lavagem"),
    HIDRATACAO("Hidratação");

    private final String descricao;

    TiposServicos(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
