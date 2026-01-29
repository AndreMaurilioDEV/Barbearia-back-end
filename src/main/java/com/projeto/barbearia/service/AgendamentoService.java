package com.projeto.barbearia.service;

import ch.qos.logback.core.net.server.Client;
import com.projeto.barbearia.controller.Dto.AgendamentoCreationDto;
import com.projeto.barbearia.entity.*;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import com.projeto.barbearia.repository.AgendamentoRepository;
import com.projeto.barbearia.repository.ServicoAgendadoRepository;
import com.projeto.barbearia.repository.ServicoRepository;
import com.projeto.barbearia.service.exceptions.AgendamentoExceptions.AgendamentoJaExiste;
import com.projeto.barbearia.service.exceptions.AgendamentoExceptions.AgendamentoNaoEncontrado;
import com.projeto.barbearia.service.exceptions.ServicoExceptions.ServicoNaoEncontrado;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNaoEncontrado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ProfissionalService profissionalService;
    private final UsuarioService usuarioService;
    private final ServicoRepository servicoRepository;
    private final ServicoAgendadoRepository servicoAgendadoRepository;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository, ProfissionalService profissionalService, UsuarioService usuarioService, ServicoRepository servicoRepository, ServicoAgendadoRepository servicoAgendadoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.profissionalService = profissionalService;
        this.usuarioService = usuarioService;
        this.servicoRepository = servicoRepository;
        this.servicoAgendadoRepository = servicoAgendadoRepository;
    }

    public List<Agendamento> findAll() {
        return agendamentoRepository.findAll();
    }

    public List<Agendamento> buscarAgendamentosProfissional(
            Long profissionalId,
            LocalDate data,
            StatusAgendamento status
    ) {
        if (data != null && status != null) {
            return agendamentoRepository
                    .findByProfissionalIdAndDataAndStatusAgendamento(
                            profissionalId, data, status
                    );
        }

        if (data != null) {
            return agendamentoRepository
                    .findByProfissionalIdAndData(profissionalId, data);
        }

        if (status != null) {
            return agendamentoRepository
                    .findByProfissionalIdAndStatusAgendamento(profissionalId, status);
        }

        return agendamentoRepository.findByProfissionalId(profissionalId);
    }


    public List<Agendamento> buscarAgendamentosCliente(
            Long clienteId,
            LocalDate data,
            StatusAgendamento status
    ) {
        if (data != null && status != null) {
            return agendamentoRepository
                    .findByClienteIdAndDataAndStatusAgendamento(
                            clienteId, data, status
                    );
        }

        if (data != null) {
            return agendamentoRepository
                    .findByClienteIdAndData(clienteId, data);
        }

        if (status != null) {
            return agendamentoRepository
                    .findByClienteIdAndStatusAgendamento(clienteId, status);
        }

        return agendamentoRepository.findByClienteId(clienteId);
    }


    @Transactional
    public Agendamento createAgendamento(AgendamentoCreationDto agendamentoCreationDto) throws UsuarioNaoEncontrado, AgendamentoJaExiste, ServicoNaoEncontrado {

        boolean ocupado = agendamentoRepository.existsByDataAndHorarioAndBarbeiroId(
                agendamentoCreationDto.data(),
                agendamentoCreationDto.horario(),
                agendamentoCreationDto.barbeiroId()
        );

        if (ocupado) {
            throw new AgendamentoJaExiste();
        }

        Usuario usuario = usuarioService.findById(agendamentoCreationDto.clienteId());
        Profissional profissional = profissionalService.findById(agendamentoCreationDto.barbeiroId());

        List<Servico> servicos = servicoRepository.findAllById(agendamentoCreationDto.servicosIds());

        double total = servicos.stream()
                .mapToDouble(Servico::getPreco)
                .sum();

        Agendamento agendamento = new Agendamento();
        agendamento.setUsuario(usuario);
        agendamento.setProfissional(profissional);
        agendamento.setData(agendamentoCreationDto.data());
        agendamento.setHorario(agendamentoCreationDto.horario());
        agendamento.setValorTotal(total);

        List<ServicoAgendado> servicosAgendados = servicos.stream()
                .map(servico -> new ServicoAgendado(agendamento, servico))
                .toList();

        agendamento.setServicoAgendadoList(servicosAgendados);
        agendamentoRepository.save(agendamento);
        return agendamento;
    }


    public Agendamento cancelarAgendamento(Long id) throws AgendamentoNaoEncontrado {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(AgendamentoNaoEncontrado::new);
        agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);
        return agendamentoRepository.save(agendamento);
    }

}

