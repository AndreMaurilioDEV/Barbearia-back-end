package com.projeto.barbearia.service.exceptions.DisponibilidadeExceptions;

import com.projeto.barbearia.service.exceptions.NotFoundException;

public class DisponibilidadeNaoEncontrada extends NotFoundException {
    public DisponibilidadeNaoEncontrada() {
        super("Informações de disponibilidade não encontrada");
    }
}
