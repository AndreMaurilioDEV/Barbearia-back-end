package com.projeto.barbearia.entity.roles;

public enum TipoRecompensaFidelidade {
        DESCONTO_VALOR("Desconto em valor fixo"),
        DESCONTO_PERCENTUAL("Desconto em percentual"),
        SERVICO_GRATIS("Serviço gratuito"),
        UPGRADE_SERVICO("Upgrade de serviço"),
        BRINDE("Brinde ou presente"),

        private final String descricao;

        TipoRecompensaFidelidade(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }

}
