package com.projeto.barbearia.service.exceptions.AgendamentoExceptions;

import com.projeto.barbearia.service.exceptions.NotFoundException;

public class AgendamentoNaoEncontrado extends NotFoundException {
    public AgendamentoNaoEncontrado() {
        super("Agendamento não encontrado");
    }
}
