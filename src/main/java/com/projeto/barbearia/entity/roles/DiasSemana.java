package com.projeto.barbearia.entity.roles;


    public enum DiasSemana {
        SEGUNDA("Segunda-feira"),
        TERCA("Terca-feira"),
        QUARTA("Quarta-feira"),
        QUINTA("Quinta-feira"),
        SEXTA("Sexta-feira"),
        SABADO("Sábado"),
        DOMINGO("Domingo");

        private final String descricao;

        DiasSemana(String descricao) {
            this.descricao = descricao;
        }
        public String getDescricao() {
            return descricao;
        }
    }


