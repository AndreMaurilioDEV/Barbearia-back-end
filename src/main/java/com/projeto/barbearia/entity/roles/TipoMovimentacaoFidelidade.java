package com.projeto.barbearia.entity.roles;

public enum TipoMovimentacaoFidelidade {
        GANHO_AGENDAMENTO("Ganhos por Agendamento"),
        GANHO_INDICACAO("Ganhos por Indicação"),
        GANHO_PROMOCAO("Ganhos por Promoção"),
        RESGATE_DESCONTO("Resgate por Desconto"),
        RESGATE_SERVICO("Resgate por Serviço"),
        ESTORNO("Estorno de Pontos"),
        EXPIRACAO("Expiração de Pontos"),
        AJUSTE_MANUAL("Ajuste Manual");

        private final String descricao;

        TipoMovimentacaoFidelidade(String descricao) {
            this.descricao = descricao;
        }
        public String getDescricao() {
            return descricao;
        }
}
