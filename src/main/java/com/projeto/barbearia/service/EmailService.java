package com.projeto.barbearia.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String rementente;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Transactional
    public String sendEmail(String destinario, String assunto, String mensagem) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(rementente);
            helper.setTo(destinario);
            helper.setSubject(assunto);
            helper.setText(mensagem, true);
            mailSender.send(mimeMessage);
            return "Email enviado com sucesso";
        } catch(MessagingException e) {
            throw new IllegalStateException("Falha ao enviar e-mail", e);
        }
    }


}
