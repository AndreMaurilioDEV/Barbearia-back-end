package com.projeto.barbearia.controller;

import com.projeto.barbearia.controller.Dto.AuthDto;
import com.projeto.barbearia.controller.Dto.TokenDto;
import com.projeto.barbearia.security.Role;
import com.projeto.barbearia.service.JwtService;
import com.projeto.barbearia.service.exceptions.RoleNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody AuthDto authDto) throws RoleNotFound {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                authDto.email(), authDto.password());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        String role = authentication.getAuthorities().stream()
                .findFirst()
                .orElseThrow(RoleNotFound::new)
                .getAuthority();
        String token = jwtService.generateToken(authentication.getName(), role);
        return new TokenDto(token);
    }
}
