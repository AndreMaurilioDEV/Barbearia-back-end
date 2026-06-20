package com.projeto.barbearia.service;

import ch.qos.logback.core.net.server.Client;
import com.projeto.barbearia.controller.Dto.AgendamentoCreationDto;
import com.projeto.barbearia.entity.*;
import com.projeto.barbearia.entity.roles.DiasSemana;
import com.projeto.barbearia.entity.roles.OrigemEntrada;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import com.projeto.barbearia.repository.AgendamentoRepository;
import com.projeto.barbearia.repository.ProfissionalDisponibilidadeRepository;
import com.projeto.barbearia.repository.ServicoAgendadoRepository;
import com.projeto.barbearia.repository.ServicoRepository;
import com.projeto.barbearia.service.exceptions.AgendamentoExceptions.AgendamentoJaExiste;
import com.projeto.barbearia.service.exceptions.AgendamentoExceptions.AgendamentoNaoEncontrado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {

    private static final int INTERVALO_BLOCO_MINUTOS = 15;
    private static final int MARGEM_TECNICA_MINUTOS = 10;

    private final AgendamentoRepository agendamentoRepository;
    private final ProfissionalService profissionalService;
    private final UsuarioService usuarioService;
    private final ServicoRepository servicoRepository;
    private final ServicoAgendadoRepository servicoAgendadoRepository;
    private final ProfissionalDisponibilidadeRepository profissionalDisponibilidadeRepository;
    private final FidelidadeService fidelidadeService;

    @Autowired
    public AgendamentoService(AgendamentoRepository agendamentoRepository, ProfissionalService profissionalService, UsuarioService usuarioService, ServicoRepository servicoRepository, ServicoAgendadoRepository servicoAgendadoRepository, ProfissionalDisponibilidadeRepository profissionalDisponibilidadeRepository, FidelidadeService fidelidadeService) {
        this.agendamentoRepository = agendamentoRepository;
        this.profissionalService = profissionalService;
        this.usuarioService = usuarioService;
        this.servicoRepository = servicoRepository;
        this.servicoAgendadoRepository = servicoAgendadoRepository;
        this.profissionalDisponibilidadeRepository = profissionalDisponibilidadeRepository;
        this.fidelidadeService = fidelidadeService;
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
                    .findByUsuarioIdAndDataAndStatusAgendamento(
                            clienteId, data, status
                    );
        }

        if (data != null) {
            return agendamentoRepository
                    .findByUsuarioIdAndData(clienteId, data);
        }

        if (status != null) {
            return agendamentoRepository
                    .findByUsuarioIdAndStatusAgendamento(clienteId, status);
        }

        return agendamentoRepository.findByUsuarioId(clienteId);
    }


    @Transactional
    public Agendamento createAgendamento(AgendamentoCreationDto agendamentoCreationDto)  {

        if (!horariosDisponiveis(
                agendamentoCreationDto.barbeiroId(),
                agendamentoCreationDto.data(),
                agendamentoCreationDto.servicosIds()
        ).contains(agendamentoCreationDto.horario())) {
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
        agendamento.setOrigemEntrada(OrigemEntrada.AGENDAMENTO);
        agendamento.setValorTotal(total);

        List<ServicoAgendado> servicosAgendados = servicos.stream()
                .map(servico -> new ServicoAgendado(agendamento, servico))
                .toList();

        agendamento.setServicoAgendadoList(servicosAgendados);
        agendamentoRepository.save(agendamento);
        return agendamento;
    }

    public List<LocalTime> horariosDisponiveis(Long profissionalId, LocalDate data, List<Long> servicosIds) {
        List<Servico> servicos = servicoRepository.findAllById(servicosIds);
        int duracaoTotal = calcularDuracaoTotal(servicos);

        ProfissionalDisponibilidade disponibilidade = profissionalDisponibilidadeRepository
                .findByProfissionalIdAndDiaDaSemana(profissionalId, converterDiaSemana(data.getDayOfWeek()))
                .orElseThrow(() -> new RuntimeException("Profissional não possui disponibilidade cadastrada para essa data"));

        LocalTime inicio = disponibilidade.getHora_inicio();
        LocalTime fim = disponibilidade.getHora_fim();
        List<Agendamento> agendamentosDoDia = agendamentoRepository.findByProfissionalIdAndData(profissionalId, data);
        List<LocalTime> horarios = new ArrayList<>();

        for (LocalTime horario = inicio; !horario.plusMinutes(duracaoTotal).isAfter(fim); horario = horario.plusMinutes(INTERVALO_BLOCO_MINUTOS)) {
            LocalTime inicioHorario = horario;
            LocalTime fimHorario = inicioHorario.plusMinutes(duracaoTotal);

            if (agendamentosDoDia.stream().noneMatch(agendamento -> conflita(inicioHorario, fimHorario, agendamento))) {
                horarios.add(inicioHorario);
            }
        }

        return horarios;
    }

    private int calcularDuracaoTotal(List<Servico> servicos) {
        int duracaoServicos = servicos.stream()
                .map(Servico::getDuracaoMinutos)
                .filter(duracao -> duracao != null && duracao > 0)
                .mapToInt(Integer::intValue)
                .sum();

        return duracaoServicos + MARGEM_TECNICA_MINUTOS;
    }

    private boolean conflita(LocalTime inicioNovo, LocalTime fimNovo, Agendamento agendamentoExistente) {
        if (agendamentoExistente.getStatusAgendamento() == StatusAgendamento.CANCELADO) {
            return false;
        }

        LocalTime inicioExistente = agendamentoExistente.getHorario();
        LocalTime fimExistente = inicioExistente.plusMinutes(calcularDuracaoAgendamentoExistente(agendamentoExistente));

        return inicioNovo.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente);
    }

    private int calcularDuracaoAgendamentoExistente(Agendamento agendamento) {
        int duracao = agendamento.getServicoAgendadoList().stream()
                .map(ServicoAgendado::getServico)
                .map(Servico::getDuracaoMinutos)
                .filter(duracaoMinutos -> duracaoMinutos != null && duracaoMinutos > 0)
                .mapToInt(Integer::intValue)
                .sum();

        return duracao + MARGEM_TECNICA_MINUTOS;
    }

    private DiasSemana converterDiaSemana(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> DiasSemana.SEGUNDA;
            case TUESDAY -> DiasSemana.TERCA;
            case WEDNESDAY -> DiasSemana.QUARTA;
            case THURSDAY -> DiasSemana.QUINTA;
            case FRIDAY -> DiasSemana.SEXTA;
            case SATURDAY -> DiasSemana.SABADO;
            case SUNDAY -> DiasSemana.DOMINGO;
        };
    }


    public Agendamento cancelarAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(AgendamentoNaoEncontrado::new);
        agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);
        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento iniciarAtendimento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(AgendamentoNaoEncontrado::new);

        if (agendamento.getStatusAgendamento() != StatusAgendamento.CONFIRMADO) {
            throw new RuntimeException("Apenas agendamentos confirmados podem ser iniciados.");
        }

        agendamento.setStatusAgendamento(StatusAgendamento.EM_ATENDIMENTO);
        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento concluirAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(AgendamentoNaoEncontrado::new);

        if (agendamento.getStatusAgendamento() != StatusAgendamento.EM_ATENDIMENTO) {
            throw new RuntimeException("Apenas agendamentos em atendimento podem ser concluídos.");
        }

        agendamento.setStatusAgendamento(StatusAgendamento.CONCLUIDO);
        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        fidelidadeService.gerarPontosPorAgendamentoConcluido(id);
        return agendamentoSalvo;
    }

    @Transactional
    public Agendamento adicionarClienteAFila(AgendamentoCreationDto agendamentoCreationDto) {

        Usuario cliente = usuarioService.findById(agendamentoCreationDto.clienteId());
        Profissional profissional = profissionalService.findById(agendamentoCreationDto.barbeiroId());

        List<Servico> servicos = servicoRepository.findAllById(agendamentoCreationDto.servicosIds());

        double total = servicos.stream()
                .mapToDouble(Servico::getPreco)
                .sum();

        LocalDate data = agendamentoCreationDto.data() != null ? agendamentoCreationDto.data() : LocalDate.now();

        List<Agendamento> filaAtual = agendamentoRepository.findByProfissionalIdAndDataAndStatusAgendamentoOrderByPosicaoFilaAsc(
                profissional.getId(),
                data,
                StatusAgendamento.NA_FILA
        );

        LocalTime horarioPrevisao = calcularPrevisao(profissional.getId(), data, filaAtual);

        Integer posicaoFila = filaAtual.size() + 1;

        Agendamento agendamento = new Agendamento();
        agendamento.setUsuario(cliente);
        agendamento.setProfissional(profissional);
        agendamento.setData(agendamentoCreationDto.data());
        agendamento.setOrigemEntrada(OrigemEntrada.FILA_PRESENCIAL);
        agendamento.setPosicaoFila(posicaoFila);
        agendamento.setHorarioPrevisto(horarioPrevisao);
        List<ServicoAgendado> servicosAgendados = servicos.stream()
                .map(servico -> new ServicoAgendado(agendamento, servico))
                .toList();

        agendamento.setServicoAgendadoList(servicosAgendados);
        agendamento.setValorTotal(total);
        return agendamentoRepository.save(agendamento);
    }

    private LocalTime calcularPrevisao(Long profissionalId, LocalDate data, List<Agendamento> filaAtual) {
        LocalTime previsao = LocalTime.now();
        Optional<Agendamento> atendimentoEmAndamento = agendamentoRepository.findFirstByProfissionalIdAndDataAndStatusAgendamento(
                profissionalId, data, StatusAgendamento.EM_ATENDIMENTO);

        if(atendimentoEmAndamento.isPresent()) {
            previsao = previsao.plusMinutes(calcularDuracaoAgendamentoExistente(atendimentoEmAndamento.get()));
        }

        for (Agendamento agendamentoFila : filaAtual) {
            previsao = previsao.plusMinutes(calcularDuracaoAgendamentoExistente(agendamentoFila));
        }

        return previsao;

    }

    public Integer tamanhoFila(Long profissionalId, LocalDate localDate) {
        return agendamentoRepository.findByProfissionalIdAndDataAndStatusAgendamentoOrderByPosicaoFilaAsc(
                profissionalId,
                localDate,
                StatusAgendamento.NA_FILA
        ).size();

    }





}

