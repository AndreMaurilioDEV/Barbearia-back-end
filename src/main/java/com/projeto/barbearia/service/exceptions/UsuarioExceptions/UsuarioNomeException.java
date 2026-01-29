package com.projeto.barbearia.service.exceptions.UsuarioExceptions;

import com.projeto.barbearia.service.exceptions.NotFoundException;

public class UsuarioNomeException extends RuntimeException {
    public UsuarioNomeException(String message) {
        super(message);
    }
}
