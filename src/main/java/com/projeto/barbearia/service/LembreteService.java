package com.projeto.barbearia.service;

import com.projeto.barbearia.entity.Agendamento;
import com.projeto.barbearia.entity.roles.StatusAgendamento;
import com.projeto.barbearia.repository.AgendamentoRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LembreteService {

    private final AgendamentoRepository agendamentoRepository;
    private final EmailService  emailService;

    public LembreteService(AgendamentoRepository agendamentoRepository, EmailService emailService) {
        this.agendamentoRepository = agendamentoRepository;
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 300000)
    public void enviarLembretes() {
        LocalDateTime agora = LocalDateTime.now();

        LocalDateTime em24h = agora.plusHours(24);
        LocalDateTime em1h  = agora.plusHours(1);

        verificarEEnviar(em24h, "24 horas");
        verificarEEnviar(em1h,  "1 hora");
    }

    private void verificarEEnviar(LocalDateTime alvo, String tempo) {
        LocalDate data    = alvo.toLocalDate();
        LocalTime horario = alvo.toLocalTime();

        List<Agendamento> agendamentos = agendamentoRepository
                .findByDataAndHorarioBetweenAndStatusAgendamento(
                        data,
                        horario.minusMinutes(2),
                        horario.plusMinutes(2),
                        StatusAgendamento.CONFIRMADO
                );

        for (Agendamento agendamento : agendamentos) {
            if (tempo.equals("24 horas") && Boolean.TRUE.equals(agendamento.getLembrete24hEnviado())) continue;
            if (tempo.equals("1 hora")   && Boolean.TRUE.equals(agendamento.getLembrete1hEnviado()))  continue;

            String email   = agendamento.getUsuario().getEmail();
            String assunto = "✂️ Lembrete: seu agendamento na barbearia em " + tempo;
            String corpo   = montarCorpo(agendamento, tempo);

            emailService.sendEmail(email, assunto, corpo);

            if (tempo.equals("24 horas")) agendamento.setLembrete24hEnviado(true);
            if (tempo.equals("1 hora"))   agendamento.setLembrete1hEnviado(true);
            agendamentoRepository.save(agendamento);
        }
    }


    private String montarCorpo(Agendamento agendamento, String tempo) {
        DateTimeFormatter dataFormatter    = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter horarioFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String nomeCliente      = agendamento.getUsuario().getNome();
        String nomeProfissional = agendamento.getProfissional().getNome();
        String data             = agendamento.getData().format(dataFormatter);
        String horario          = agendamento.getHorario().format(horarioFormatter);

        String servicosHtml = agendamento.getServicoAgendadoList().stream()
                .map(sa -> """
                    <tr>
                        <td style="padding: 8px 12px; border-bottom: 1px solid #f0e6d3; color: #555;">
                            %s
                        </td>
                        <td style="padding: 8px 12px; border-bottom: 1px solid #f0e6d3; color: #8B6914; font-weight: bold; text-align: right;">
                            R$ %.2f
                        </td>
                    </tr>
                """.formatted(
                        sa.getServico().getTiposServicos().getDescricao(),
                        sa.getServico().getPreco()
                ))
                .collect(Collectors.joining());

        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
            </head>
            <body style="margin: 0; padding: 0; background-color: #f5f0e8; font-family: 'Georgia', serif;">
 
                <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f5f0e8; padding: 40px 0;">
                    <tr>
                        <td align="center">
                            <table width="600" cellpadding="0" cellspacing="0" style="max-width: 600px; width: 100%%;">
 
                               
                                <tr>
                                    <td style="background-color: #1a1a1a; padding: 35px 40px; border-radius: 12px 12px 0 0; text-align: center;">
                                        <p style="margin: 0 0 6px 0; font-size: 13px; color: #c9a84c; letter-spacing: 4px; text-transform: uppercase;">Barbearia</p>
                                        <h1 style="margin: 0; font-size: 32px; color: #ffffff; letter-spacing: 2px;">✂ BARBER SHOP</h1>
                                        <div style="margin-top: 14px; width: 50px; height: 2px; background-color: #c9a84c; display: inline-block;"></div>
                                    </td>
                                </tr>
 
                               
                                <tr>
                                    <td style="background-color: #c9a84c; padding: 14px 40px; text-align: center;">
                                        <p style="margin: 0; font-size: 14px; color: #1a1a1a; font-weight: bold; letter-spacing: 2px; text-transform: uppercase;">
                                            ⏰ Seu agendamento é em %s
                                        </p>
                                    </td>
                                </tr>
 
                                <!-- Corpo -->
                                <tr>
                                    <td style="background-color: #ffffff; padding: 40px 40px 30px 40px;">
 
                                        <p style="margin: 0 0 8px 0; font-size: 22px; color: #1a1a1a; font-weight: bold;">
                                            Olá, %s! 👋
                                        </p>
                                        <p style="margin: 0 0 28px 0; font-size: 15px; color: #666; line-height: 1.6;">
                                            Passando para lembrar que você tem um agendamento confirmado conosco. Estamos te esperando!
                                        </p>
 
    
                                        <table width="100%%" cellpadding="0" cellspacing="0"
                                               style="background-color: #faf7f2; border: 1px solid #e8dcc8; border-radius: 10px; margin-bottom: 28px;">
                                            <tr>
                                                <td style="padding: 20px 24px; border-bottom: 1px solid #e8dcc8;">
                                                    <p style="margin: 0; font-size: 11px; color: #999; text-transform: uppercase; letter-spacing: 1px;">Profissional</p>
                                                    <p style="margin: 4px 0 0 0; font-size: 17px; color: #1a1a1a; font-weight: bold;">💈 %s</p>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="padding: 20px 24px; border-bottom: 1px solid #e8dcc8;">
                                                    <p style="margin: 0; font-size: 11px; color: #999; text-transform: uppercase; letter-spacing: 1px;">Data</p>
                                                    <p style="margin: 4px 0 0 0; font-size: 17px; color: #1a1a1a; font-weight: bold;">📅 %s</p>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="padding: 20px 24px;">
                                                    <p style="margin: 0; font-size: 11px; color: #999; text-transform: uppercase; letter-spacing: 1px;">Horário</p>
                                                    <p style="margin: 4px 0 0 0; font-size: 17px; color: #1a1a1a; font-weight: bold;">🕐 %s</p>
                                                </td>
                                            </tr>
                                        </table>
 
                                        
                                        <p style="margin: 0 0 12px 0; font-size: 13px; color: #999; text-transform: uppercase; letter-spacing: 1px;">Serviços agendados</p>
                                        <table width="100%%" cellpadding="0" cellspacing="0"
                                               style="border: 1px solid #e8dcc8; border-radius: 10px; overflow: hidden; margin-bottom: 28px;">
                                            <thead>
                                                <tr style="background-color: #1a1a1a;">
                                                    <th style="padding: 10px 12px; text-align: left; color: #c9a84c; font-size: 12px; letter-spacing: 1px; text-transform: uppercase;">Serviço</th>
                                                    <th style="padding: 10px 12px; text-align: right; color: #c9a84c; font-size: 12px; letter-spacing: 1px; text-transform: uppercase;">Valor</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                %s
                                            </tbody>
                                            <tfoot>
                                                <tr style="background-color: #faf7f2;">
                                                    <td style="padding: 12px; color: #1a1a1a; font-weight: bold; font-size: 15px;">Total</td>
                                                    <td style="padding: 12px; color: #c9a84c; font-weight: bold; font-size: 15px; text-align: right;">R$ %.2f</td>
                                                </tr>
                                            </tfoot>
                                        </table>
 
                                      
                                        <table width="100%%" cellpadding="0" cellspacing="0"
                                               style="background-color: #fff8e8; border-left: 4px solid #c9a84c; border-radius: 0 8px 8px 0; margin-bottom: 10px;">
                                            <tr>
                                                <td style="padding: 14px 18px;">
                                                    <p style="margin: 0; font-size: 13px; color: #8B6914; line-height: 1.6;">
                                                        ⚠️ <strong>Precisa cancelar?</strong> Entre em contato com antecedência para liberar o horário para outros clientes.
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
 
                                    </td>
                                </tr>
 
                              
                                <tr>
                                    <td style="background-color: #1a1a1a; padding: 24px 40px; border-radius: 0 0 12px 12px; text-align: center;">
                                        <p style="margin: 0 0 6px 0; font-size: 13px; color: #c9a84c; letter-spacing: 1px;">✂ Barber Shop</p>
                                        <p style="margin: 0; font-size: 12px; color: #666;">
                                            Este é um email automático, por favor não responda.
                                        </p>
                                    </td>
                                </tr>
 
                            </table>
                        </td>
                    </tr>
                </table>
 
            </body>
            </html>
        """.formatted(
                tempo,
                nomeCliente,
                nomeProfissional,
                data,
                horario,
                servicosHtml,
                agendamento.getValorTotal()
        );
    }
}
