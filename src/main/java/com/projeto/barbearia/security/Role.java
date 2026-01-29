package com.projeto.barbearia.security;

public enum Role {
    PROFISSIONAL("ROLE_PROFISSIONAL"),
    CLIENTE("ROLE_CLIENTE"),
    ADMIN("ROLE_ADMIN");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
