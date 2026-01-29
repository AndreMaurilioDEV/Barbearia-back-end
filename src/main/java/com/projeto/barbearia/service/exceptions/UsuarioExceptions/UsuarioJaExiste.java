package com.projeto.barbearia.service.exceptions.UsuarioExceptions;

public class UsuarioJaExiste extends RuntimeException {
    public UsuarioJaExiste(String message) {
        super(message);
     }
}

