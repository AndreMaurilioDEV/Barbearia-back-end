package com.projeto.barbearia.service.exceptions.ServicoExceptions;

public class ServicoJaExiste extends RuntimeException {
    public ServicoJaExiste() {
        super("O Serviço já existe.");
    }
}

