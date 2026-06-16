package com.projeto.barbearia.service;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.MovimentacaoFidelidade;
import com.projeto.barbearia.entity.Pagamento;
import com.projeto.barbearia.entity.Servico;
import com.projeto.barbearia.entity.ServicoAgendado;
import com.projeto.barbearia.entity.Usuario;
import com.projeto.barbearia.entity.roles.MetodoPagamento;
import com.projeto.barbearia.entity.roles.NivelFidelidade;
import com.projeto.barbearia.entity.roles.TipoMovimentacaoFidelidade;
import com.projeto.barbearia.repository.AgendamentoRepository;
import com.projeto.barbearia.repository.MovimentacaoFidelidadeRepository;
import com.projeto.barbearia.repository.UsuarioRepository;
import com.projeto.barbearia.service.exceptions.AgendamentoExceptions.AgendamentoNaoEncontrado;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNaoEncontrado;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class FidelidadeService {

    private static final int PONTOS_AGENDAMENTO_CONCLUIDO = 10;
    private static final int PONTOS_PAGAMENTO_ONLINE = 5;
    private static final int PONTOS_ANIVERSARIO = 20;
    private static final int PONTOS_INDICACAO = 15;

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MovimentacaoFidelidadeRepository movimentacaoFidelidadeRepository;

    public FidelidadeService(AgendamentoRepository agendamentoRepository,
                             UsuarioRepository usuarioRepository,
                             MovimentacaoFidelidadeRepository movimentacaoFidelidadeRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.movimentacaoFidelidadeRepository = movimentacaoFidelidadeRepository;
    }

    @Transactional
    public void gerarPontosPorAgendamentoConcluido(Long agendamentoId) {
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(AgendamentoNaoEncontrado::new);

        if (Boolean.TRUE.equals(agendamento.getPontosFidelidadeGerados())
                || movimentacaoFidelidadeRepository.existsByAgendamentoIdAndTipo(agendamentoId, TipoMovimentacaoFidelidade.GANHO_AGENDAMENTO)) {
            return;
        }

        Usuario usuario = agendamento.getUsuario();
        int pontosBase = calcularPontosBase(agendamento);
        int pontosComBonusNivel = aplicarBonusNivel(usuario.getNivelFidelidade(), pontosBase);
        int pontosFinais = isDiaFraco(agendamento.getData()) ? pontosComBonusNivel * 2 : pontosComBonusNivel;

        creditarPontos(usuario, agendamento, pontosFinais, TipoMovimentacaoFidelidade.GANHO_AGENDAMENTO, montarDescricaoAgendamento(agendamento, pontosBase, pontosFinais));

        agendamento.setPontosFidelidadeGerados(true);
        agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void gerarPontosPorIndicacao(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontrado("Cliente não encontrado"));

        int pontos = aplicarBonusNivel(usuario.getNivelFidelidade(), PONTOS_INDICACAO);
        creditarPontos(usuario, null, pontos, TipoMovimentacaoFidelidade.GANHO_INDICACAO, "Pontos por indicação de novo cliente");
    }

    private int calcularPontosBase(Agendamento agendamento) {
        int pontos = PONTOS_AGENDAMENTO_CONCLUIDO;
        pontos += calcularPontosPorServicos(agendamento.getServicoAgendadoList());

        if (temPagamentoOnline(agendamento.getPagamentos())) {
            pontos += PONTOS_PAGAMENTO_ONLINE;
        }

        if (isAniversarioDoCliente(agendamento.getUsuario(), agendamento.getData())) {
            pontos += PONTOS_ANIVERSARIO;
        }

        return pontos;
    }

    private int calcularPontosPorServicos(List<ServicoAgendado> servicosAgendados) {
        if (servicosAgendados == null) {
            return 0;
        }

        return servicosAgendados.stream()
                .map(ServicoAgendado::getServico)
                .map(Servico::getPreco)
                .filter(preco -> preco != null && preco > 0)
                .mapToInt(preco -> (int) Math.round(preco))
                .sum();
    }

    private boolean temPagamentoOnline(List<Pagamento> pagamentos) {
        if (pagamentos == null) {
            return false;
        }

        return pagamentos.stream()
                .map(Pagamento::getMetodoPagamento)
                .anyMatch(metodo -> metodo == MetodoPagamento.PIX || metodo == MetodoPagamento.CARTAO);
    }

    private boolean isAniversarioDoCliente(Usuario usuario, LocalDate dataAgendamento) {
        if (usuario.getDataNascimento() == null || dataAgendamento == null) {
            return false;
        }

        return usuario.getDataNascimento().getDayOfMonth() == dataAgendamento.getDayOfMonth()
                && usuario.getDataNascimento().getMonth() == dataAgendamento.getMonth();
    }

    private boolean isDiaFraco(LocalDate data) {
        if (data == null) {
            return false;
        }

        DayOfWeek dia = data.getDayOfWeek();
        return dia == DayOfWeek.TUESDAY || dia == DayOfWeek.WEDNESDAY;
    }

    private int aplicarBonusNivel(NivelFidelidade nivel, int pontos) {
        if (nivel == NivelFidelidade.PRATA) {
            return (int) Math.round(pontos * 1.05);
        }

        if (nivel == NivelFidelidade.OURO) {
            return (int) Math.round(pontos * 1.10);
        }

        return pontos;
    }

    private void creditarPontos(Usuario usuario, Agendamento agendamento, int pontos, TipoMovimentacaoFidelidade tipo, String descricao) {
        usuario.setFidelityPoints(valorSeguro(usuario.getFidelityPoints()) + pontos);
        usuario.setTotalPontosGanhos(valorSeguro(usuario.getTotalPontosGanhos()) + pontos);
        usuario.setNivelFidelidade(calcularNivel(usuario.getTotalPontosGanhos()));

        MovimentacaoFidelidade movimentacao = new MovimentacaoFidelidade();
        movimentacao.setUsuario(usuario);
        movimentacao.setAgendamento(agendamento);
        movimentacao.setPontos(pontos);
        movimentacao.setTipo(tipo);
        movimentacao.setDescricao(descricao);

        usuarioRepository.save(usuario);
        movimentacaoFidelidadeRepository.save(movimentacao);
    }

    private NivelFidelidade calcularNivel(Integer totalPontosGanhos) {
        int total = valorSeguro(totalPontosGanhos);

        if (total >= 1000) {
            return NivelFidelidade.DIAMANTE;
        }

        if (total >= 500) {
            return NivelFidelidade.OURO;
        }

        if (total >= 200) {
            return NivelFidelidade.PRATA;
        }

        return NivelFidelidade.BRONZE;
    }

    private int valorSeguro(Integer valor) {
        return valor == null ? 0 : valor;
    }

    private String montarDescricaoAgendamento(Agendamento agendamento, int pontosBase, int pontosFinais) {
        String descricao = "Pontos por agendamento concluído";

        if (temPagamentoOnline(agendamento.getPagamentos())) {
            descricao += ", pagamento online";
        }

        if (isAniversarioDoCliente(agendamento.getUsuario(), agendamento.getData())) {
            descricao += ", bônus de aniversário";
        }

        if (isDiaFraco(agendamento.getData())) {
            descricao += ", pontos em dobro por dia fraco";
        }

        return descricao + ". Base: " + pontosBase + ", total creditado: " + pontosFinais;
    }
}
