package com.projeto.barbearia.service.exceptions.UsuarioExceptions;


import com.projeto.barbearia.service.exceptions.NotFoundException;

public class UsuarioNaoEncontrado extends NotFoundException {
    public UsuarioNaoEncontrado(String message) {
        super(message);
    }
}
