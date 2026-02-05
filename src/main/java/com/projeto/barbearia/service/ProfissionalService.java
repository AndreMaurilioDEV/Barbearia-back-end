package com.projeto.barbearia.service;


import com.projeto.barbearia.controller.Dto.ProfissionalCreationDto;

import com.projeto.barbearia.entity.Profissional;

import com.projeto.barbearia.repository.ProfissionalRepository;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioJaExiste;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNaoEncontrado;
import com.projeto.barbearia.service.exceptions.UsuarioExceptions.UsuarioNomeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    @Autowired
    private  EmailService emailService;

    @Autowired
    public ProfissionalService(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;

    }

    public List<Profissional> findAll() {
        return profissionalRepository.findAll();
    }


    public Profissional findByEmail(String email) {
        return profissionalRepository.findByEmail(email).orElse(null);
    }

    public Profissional findById(Long id) {
        return profissionalRepository.findById(id).orElse(null);
    }

    @Transactional
    public Void deleteProfissional(Long idBarbeiro) throws UsuarioNaoEncontrado {
        profissionalRepository.deleteById(idBarbeiro);
        return null;
    }

    @Transactional
    public Profissional createProfissional(ProfissionalCreationDto profissionalCreationDto) throws UsuarioJaExiste, UsuarioNomeException {
        if (profissionalRepository.findByEmail(profissionalCreationDto.email()).isPresent()) {
            throw new UsuarioJaExiste("Barbeiro com esse email já existe");
        }

        if (profissionalCreationDto.nome() == null || profissionalCreationDto.nome().isEmpty()) {
            throw new UsuarioNomeException("Nome do barbeiro não pode ser vazio");
        }
        String tempPassword = UUID.randomUUID().toString().substring(0,8);
        String hashedPassword = new BCryptPasswordEncoder()
                .encode(tempPassword);
        Profissional profissional = new Profissional();
        profissional.setNome(profissionalCreationDto.nome());
        profissional.setEmail(profissionalCreationDto.email());
        profissional.setTelefone(profissionalCreationDto.telefone());
        profissional.setPassword(hashedPassword);
        enviaEmailDeCriacao(profissional, tempPassword);
        return profissionalRepository.save(profissional);
    }

    @Transactional
    public Profissional AtualizaProfissional(Long id, Profissional profissional) {
        Profissional profissionalfromDB = profissionalRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontrado("Barbeiro não encontrado"));
        profissionalfromDB.setNome(profissional.getNome());
        profissionalfromDB.setTelefone(profissional.getTelefone());
        return profissionalRepository.save(profissional);
    }


    public void AtualizaStatusProfissional(Long id, Boolean ativo) {
        Profissional profissional = profissionalRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontrado("Barbeiro não encontrado"));
        profissional.setAtivo(ativo);
        profissionalRepository.save(profissional);
    }

    private void enviaEmailDeCriacao(Profissional barbeiro, String tempPassword) {
        String message = "<h3>Ola," + barbeiro.getNome() + "você está recebendo um email de cadastro</h3>" +
                " <p>Use a senha aleatória" + tempPassword + " para efetuar o primeiro login. </p>" +
                "<p><strong>Após isso, faça a alteração da senha nas configurações da sua conta.</strong></p>";
        emailService.sendEmail(barbeiro.getUsername(), "Novo Cadastro", message);
    }

    public Profissional AtualizarSenhaPadrao(String email, String newPassword, String currentPassword) {
        Profissional profissional = findByEmail(email);
        if (!new BCryptPasswordEncoder().matches(currentPassword, profissional.getPassword())) {
            throw new IllegalArgumentException("Senha atual não está correta.");
        }
        if (newPassword == null || newPassword.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }
        String hashedPassword = new BCryptPasswordEncoder().encode(newPassword);
        profissional.setPassword(hashedPassword);
        sendUpdatePasswordEmail(profissional);
        return profissionalRepository.save(profissional);
    }

    private void sendUpdatePasswordEmail(Profissional profissional) {
        String message = "<h3>Olá,"+ profissional.getNome() + "</h3>"+
                "<p>Você alterou sua senha com sucesso.</p>" +
                "<p>Se não foi você, entre em contato com o suporte imediatamente.</p>";
        emailService.sendEmail(profissional.getUsername(), "Senha Alterada", message);
    }
}
