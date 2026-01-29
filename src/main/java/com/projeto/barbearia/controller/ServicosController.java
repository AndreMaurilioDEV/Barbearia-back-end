package com.projeto.barbearia.controller;

import com.projeto.barbearia.controller.Dto.ServicoCreationDto;
import com.projeto.barbearia.controller.Dto.ServicoDto;
import com.projeto.barbearia.entity.Servico;
import com.projeto.barbearia.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = "*")
public class ServicosController {
    private final ServicoService servicoService;

    @Autowired
    public ServicosController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping
    public ResponseEntity<List<ServicoDto>> listarTodosServicos() {
        List<Servico> servicos = servicoService.findAll();
        List<ServicoDto> servicoDtos = servicos.stream()
                .map(ServicoDto::fromEntity)
                .toList();
        return ResponseEntity.ok(servicoDtos);
    }

    @PostMapping
    public ResponseEntity<ServicoDto> criarServico(@RequestBody ServicoCreationDto servicoCreationDto) {
        Servico servico = servicoService.createServico(servicoCreationDto);
        ServicoDto servicoDto = ServicoDto.fromEntity(servico);
        return ResponseEntity.ok(servicoDto);
    }
}
