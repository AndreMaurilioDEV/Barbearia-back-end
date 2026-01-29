package com.projeto.barbearia.service;

import com.projeto.barbearia.controller.Dto.DisponibilidadeCreationDto;

import com.projeto.barbearia.entity.Profissional;
import com.projeto.barbearia.entity.ProfissionalDisponibilidade;
import com.projeto.barbearia.entity.roles.DiasSemana;

import com.projeto.barbearia.repository.ProfissionalDisponibilidadeRepository;
import com.projeto.barbearia.service.exceptions.DisponibilidadeExceptions.DisponibilidadeNaoEncontrada;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProfissionalDisponibilidadeService {
    private final ProfissionalDisponibilidadeRepository disponibilidadeRepository;
    private final ProfissionalService profissionalService;

    @Autowired
    public ProfissionalDisponibilidadeService(ProfissionalDisponibilidadeRepository disponibilidadeRepository, ProfissionalService profissionalService) {
        this.disponibilidadeRepository = disponibilidadeRepository;
        this.profissionalService = profissionalService;
    }

    public ProfissionalDisponibilidade createProfissionalDisponibilidade(Long barbeiroId, DisponibilidadeCreationDto disponibilidadeDto) throws UsuarioNaoEncontrado {
        Profissional profissional = profissionalService.findById(barbeiroId);
        if (profissional.getNome() == null) {
            throw new UsuarioNaoEncontrado("Barbeiro não encontrado");
        }
        ProfissionalDisponibilidade profissionalDisponibilidade = new ProfissionalDisponibilidade();
        profissionalDisponibilidade.setProfissional(profissionalService.findById(disponibilidadeDto.barbeiroId()));
        profissionalDisponibilidade.setHora_fim(disponibilidadeDto.horaFim());
        profissionalDisponibilidade.setHora_inicio(disponibilidadeDto.horaInicio());
        profissionalDisponibilidade.setDia_da_semana(disponibilidadeDto.diasSemana());
        return disponibilidadeRepository.save(profissionalDisponibilidade);
    }

    public ProfissionalDisponibilidade findByDiaSemana(DiasSemana diaDaSemana) throws DisponibilidadeNaoEncontrada {
        return disponibilidadeRepository.findByDiaDaSemana(diaDaSemana).orElseThrow(DisponibilidadeNaoEncontrada::new);
    }

    public ProfissionalDisponibilidade findByProfissionalId(Long profissionalId) throws DisponibilidadeNaoEncontrada {
        return disponibilidadeRepository.findByProfissionalId(profissionalId).orElseThrow(DisponibilidadeNaoEncontrada::new);
    }
}
