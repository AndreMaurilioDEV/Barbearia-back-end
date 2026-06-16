package com.projeto.barbearia.controller;

import com.projeto.barbearia.controller.Dto.AgendamentoCreationDto;
import com.projeto.barbearia.controller.Dto.AgendamentoDto;
import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.service.AgendamentoService;
import com.projeto.barbearia.service.exceptions.AgendamentoExceptions.AgendamentoNaoEncontrado;
import com.projeto.barbearia.service.exceptions.ServicoExceptions.ServicoNaoEncontrado;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNaoEncontrado;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @Autowired
    public AgendamentoController(AgendamentoService agendamentoService) {
        this.agendamentoService = agendamentoService;
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoDto>> listarTodosAgendamentos() {
        List<Agendamento> agendamentos = agendamentoService.findAll();
        List<AgendamentoDto> agendamentoDtos = agendamentos.stream()
                .map(AgendamentoDto::fromEntity)
                .toList();
        return ResponseEntity.ok(agendamentoDtos);
    }

    @PostMapping
    public ResponseEntity<AgendamentoDto> criarAgendamento(@Valid @RequestBody AgendamentoCreationDto agendamentoCreationDto) {
        Agendamento agendamento = agendamentoService.createAgendamento(agendamentoCreationDto);
        AgendamentoDto agendamentoDto = AgendamentoDto.fromEntity(agendamento);
        return ResponseEntity.ok(agendamentoDto);
    }

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<LocalTime>> listarHorariosDisponiveis(
            @RequestParam Long profissionalId,
            @RequestParam LocalDate data,
            @RequestParam List<Long> servicosIds
    ) {
        List<LocalTime> horarios = agendamentoService.horariosDisponiveis(profissionalId, data, servicosIds);
        return ResponseEntity.ok(horarios);
    }

    @PutMapping("/{agendamentoId}/cancelar-agendamento")
    public ResponseEntity<AgendamentoDto> cancelarAgendamento(@PathVariable Long agendamentoId)  {
        Agendamento agendamentoCancelado = agendamentoService.cancelarAgendamento(agendamentoId);
        AgendamentoDto agendamentoDto = AgendamentoDto.fromEntity(agendamentoCancelado);
        return ResponseEntity.ok(agendamentoDto);
    }

    @PatchMapping("/{agendamentoId}/iniciar")
    public ResponseEntity<AgendamentoDto> iniciarAtendimento(@PathVariable Long agendamentoId) {
        Agendamento agendamentoIniciado = agendamentoService.iniciarAtendimento(agendamentoId);
        AgendamentoDto agendamentoDto = AgendamentoDto.fromEntity(agendamentoIniciado);
        return ResponseEntity.ok(agendamentoDto);
    }

    @PatchMapping("/{agendamentoId}/concluir")
    public ResponseEntity<AgendamentoDto> concluirAgendamento(@PathVariable Long agendamentoId) {
        Agendamento agendamentoConcluido = agendamentoService.concluirAgendamento(agendamentoId);
        AgendamentoDto agendamentoDto = AgendamentoDto.fromEntity(agendamentoConcluido);
        return ResponseEntity.ok(agendamentoDto);
    }

}
