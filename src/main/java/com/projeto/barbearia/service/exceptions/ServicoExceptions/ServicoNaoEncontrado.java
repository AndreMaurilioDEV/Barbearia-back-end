package com.projeto.barbearia.service.exceptions.ServicoExceptions;

import com.projeto.barbearia.service.exceptions.NotFoundException;

public class ServicoNaoEncontrado extends NotFoundException {
    public ServicoNaoEncontrado() {
        super("Serviço não encontrado");
    }
}
