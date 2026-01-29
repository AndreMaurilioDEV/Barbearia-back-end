package com.projeto.barbearia.service.exceptions.AgendamentoExceptions;

public class AgendamentoJaExiste extends RuntimeException {
    public AgendamentoJaExiste() {
        super("Já existe um agendamento para esse horário.");
     }
}
