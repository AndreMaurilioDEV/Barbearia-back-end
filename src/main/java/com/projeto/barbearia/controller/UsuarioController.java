package com.projeto.barbearia.controller;

import com.projeto.barbearia.controller.Dto.*;

import com.projeto.barbearia.entity.Usuario;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import com.projeto.barbearia.security.Role;
import com.projeto.barbearia.service.AgendamentoService;
import com.projeto.barbearia.service.AuthService;

import com.projeto.barbearia.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final AgendamentoService agendamentoService;
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(AgendamentoService agendamentoService, AuthService authService, UsuarioService usuarioService) {
        this.agendamentoService = agendamentoService;
        this.usuarioService = usuarioService;

    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> listarTodosClientes() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<UsuarioDto> usuarioDtos = usuarios.stream()
                .map(UsuarioDto::fromEntity)
                .toList();
        return ResponseEntity.ok(usuarioDtos);
    }

    @GetMapping("/{usuarioid}/agendamentos")
    public List<AgendamentoDto> listarAgendamentosCliente(
            @PathVariable Long usuarioid,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) StatusAgendamento status
    ) {
        return agendamentoService
                .buscarAgendamentosCliente(usuarioid, data, status)
                .stream()
                .map(AgendamentoDto::fromEntity)
                .toList();
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> criarUsuario(@Valid @RequestBody UsuarioCreationDto usuarioCreationDto) {
        Usuario usuario = usuarioService.createUsuario(usuarioCreationDto);
        UsuarioDto usuarioDto = UsuarioDto.fromEntity(usuario);
        return ResponseEntity.ok(usuarioDto);
    }

    @PutMapping("/{idUsuario}/atualizar")
    public ResponseEntity<Void> atualizarUsuario(@PathVariable Long idUsuario, @RequestBody UsuarioCreationDto usuarioCreationDto) {
        usuarioService.updateUsuario(usuarioCreationDto, idUsuario);
        return ResponseEntity.noContent().build();
    }

}
