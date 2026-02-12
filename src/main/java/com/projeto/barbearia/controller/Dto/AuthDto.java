package com.projeto.barbearia.controller.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthDto(@Email(message = "Email inválido") String email, @NotBlank String password) {
}
