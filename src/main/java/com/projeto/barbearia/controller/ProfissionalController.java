package com.projeto.barbearia.controller;

import com.projeto.barbearia.controller.Dto.AgendamentoDto;

import com.projeto.barbearia.controller.Dto.ProfissionalCreationDto;
import com.projeto.barbearia.controller.Dto.ProfissionalDto;
import com.projeto.barbearia.entity.Agendamento;

import com.projeto.barbearia.entity.Profissional;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import com.projeto.barbearia.service.AgendamentoService;

import com.projeto.barbearia.service.ProfissionalService;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/profissionais")
@CrossOrigin(origins = "*")
public class ProfissionalController {

    private final AgendamentoService agendamentoService;
    private final ProfissionalService profissionalService;

    @Autowired
    public ProfissionalController(AgendamentoService agendamentoService, ProfissionalService profissionalService) {
        this.agendamentoService = agendamentoService;
        this.profissionalService = profissionalService;
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalDto>> listarTodosProfissionais() {
        List<Profissional> profissionals = profissionalService.findAll();
        List<ProfissionalDto> profissionalDtos = profissionals.stream()
                .map(ProfissionalDto::fromEntity)
                .toList();
        return ResponseEntity.ok(profissionalDtos);
    }

    @GetMapping
    public ResponseEntity<ProfissionalDto> listarProfissionalPorId(@RequestParam Long profissionalId) throws UsuarioNaoEncontrado {
        Profissional profissional = profissionalService.findById(profissionalId);
        ProfissionalDto profissionalDto = ProfissionalDto.fromEntity(profissional);
        return ResponseEntity.ok(profissionalDto);
    }

    @GetMapping("/{profissionalId}/agendamentos")
    public List<AgendamentoDto> listarAgendamentosProfissional(
            @PathVariable Long profissionalId,
            @RequestParam(required = false) LocalDate data,
            @RequestParam(required = false) StatusAgendamento status
    ) {
        return agendamentoService
                .buscarAgendamentosProfissional(profissionalId, data, status)
                .stream()
                .map(AgendamentoDto::fromEntity)
                .toList();
    }

    @PostMapping
    public ResponseEntity<ProfissionalDto> criarProfissional(@RequestBody ProfissionalCreationDto profissionalCreationDto) {
        Profissional profissional = profissionalService.createProfissional(profissionalCreationDto);
        ProfissionalDto profissionalDto = ProfissionalDto.fromEntity(profissional);
        return ResponseEntity.ok(profissionalDto);
    }

    @PostMapping
    public ResponseEntity<Void> mudarSenhaPadrao(@RequestBody String email, String newPassword, String currentPassowrd) throws UsuarioNaoEncontrado {
        profissionalService.AtualizarSenhaPadrao(email, newPassword, currentPassowrd);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{profissionalId}/desativar")
    public ResponseEntity<Void> desativarProfissional(@PathVariable Long profissionalId) throws UsuarioNaoEncontrado {
        profissionalService.AtualizaStatusProfissional(profissionalId, false);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{profissionalId}/ativar")
    public ResponseEntity<Void> ativarProfissional(@PathVariable Long profissionalId) throws UsuarioNaoEncontrado {
        profissionalService.AtualizaStatusProfissional(profissionalId, true);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletar/{profissionalId}")
    public ResponseEntity<Void> deletarProfissional(@RequestParam Long profissionalId) throws UsuarioNaoEncontrado {
        profissionalService.deleteProfissional(profissionalId);
        return ResponseEntity.noContent().build();
    }
}
