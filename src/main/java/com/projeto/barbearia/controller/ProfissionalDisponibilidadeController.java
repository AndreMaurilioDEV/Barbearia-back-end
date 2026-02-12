package com.projeto.barbearia.controller;

import com.projeto.barbearia.controller.Dto.DisponibilidadeCreationDto;
import com.projeto.barbearia.controller.Dto.DisponibilidadeDto;
import com.projeto.barbearia.entity.ProfissionalDisponibilidade;
import com.projeto.barbearia.service.ProfissionalDisponibilidadeService;
import com.projeto.barbearia.service.exceptions.DisponibilidadeExceptions.DisponibilidadeNaoEncontrada;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNaoEncontrado;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profissional-disponibilidade")
public class ProfissionalDisponibilidadeController {

    private final ProfissionalDisponibilidadeService profissionalDisponibilidadeService;

    public ProfissionalDisponibilidadeController(ProfissionalDisponibilidadeService profissionalDisponibilidadeService) {
        this.profissionalDisponibilidadeService = profissionalDisponibilidadeService;
    }

    @GetMapping("/{profissionalId}/disponibilidade")
    public ResponseEntity<DisponibilidadeDto> listarDisponibilidadeProfissional(@RequestParam Long profissionalId) {
        ProfissionalDisponibilidade profissionalDisponibilidade = profissionalDisponibilidadeService.findByProfissionalId(profissionalId);
        DisponibilidadeDto disponibilidadeDto = DisponibilidadeDto.fromEntity(profissionalDisponibilidade);
        return ResponseEntity.ok(disponibilidadeDto);
    }

    @PostMapping
    public ResponseEntity<DisponibilidadeDto> criarDisponibilidadeProfissional(@Valid @RequestParam Long profissionalId, @RequestBody DisponibilidadeCreationDto disponibilidadeDto) {
        ProfissionalDisponibilidade profissionalDisponibilidade = profissionalDisponibilidadeService.createProfissionalDisponibilidade(profissionalId, disponibilidadeDto);
        DisponibilidadeDto createdDto = DisponibilidadeDto.fromEntity(profissionalDisponibilidade);
        return ResponseEntity.ok(createdDto);
    }
}
