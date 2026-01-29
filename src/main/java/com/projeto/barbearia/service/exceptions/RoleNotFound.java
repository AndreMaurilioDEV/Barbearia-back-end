package com.projeto.barbearia.service.exceptions;

public class RoleNotFound extends NotFoundException {
    public RoleNotFound() {
        super("Role não encontrada");
    }
}
