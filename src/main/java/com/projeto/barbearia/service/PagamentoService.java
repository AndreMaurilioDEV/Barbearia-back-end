package com.projeto.barbearia.service;
import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.MovimentacaoFidelidade;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import com.projeto.barbearia.repository.AgendamentoRepository;
import com.projeto.barbearia.repository.MovimentacaoFidelidadeRepository;
import com.projeto.barbearia.repository.PagamentoRepository;
import com.projeto.barbearia.service.exceptions.AgendamentoExceptions.AgendamentoJaExiste;
import com.projeto.barbearia.service.exceptions.AgendamentoExceptions.AgendamentoNaoEncontrado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final MovimentacaoFidelidadeRepository movimentacaoFidelidadeRepository;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository, AgendamentoRepository agendamentoRepository, MovimentacaoFidelidadeRepository movimentacaoFidelidadeRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.movimentacaoFidelidadeRepository = movimentacaoFidelidadeRepository;
    }

    public Agendamento formaPagamentoPresencial(Long agendamentoId) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId).orElseThrow(AgendamentoNaoEncontrado::new);
        agendamento.setStatusAgendamento(StatusAgendamento.CONFIRMADO);
        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento confirmarPagamentoOnline(Long agendamentoId) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId).orElseThrow(AgendamentoNaoEncontrado::new);
        if (agendamento.getStatusAgendamento() != StatusAgendamento.PENDENTE) {
            throw new RuntimeException("Pagamento já confirmado ou agendamento não está pendente");
        }
        agendamento.setStatusAgendamento(StatusAgendamento.CONFIRMADO);
        return agendamentoRepository.save(agendamento);
    }

}
