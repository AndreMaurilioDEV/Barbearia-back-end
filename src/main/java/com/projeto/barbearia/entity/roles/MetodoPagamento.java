package com.projeto.barbearia.entity.roles;

public enum MetodoPagamento {
    PIX("Pix"),
    CARTAO("Cartao"),
    DINHEIRO("Dinheiro");

    private final String descricao;

    MetodoPagamento(String descricao) {
        this.descricao = descricao;
    }
    public String getDescricao() {
        return descricao;
    }
}
