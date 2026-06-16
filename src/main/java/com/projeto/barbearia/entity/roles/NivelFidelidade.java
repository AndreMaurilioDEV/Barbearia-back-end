package com.projeto.barbearia.entity.roles;

public enum NivelFidelidade {
        BRONZE("Bronze"),
        PRATA("Prata"),
        OURO("Ouro"),
        DIAMANTE("Diamante");

        private final String descricao;

        NivelFidelidade(String descricao) {
            this.descricao = descricao;
        }
        public String getDescricao() {
            return descricao;
        }
}

