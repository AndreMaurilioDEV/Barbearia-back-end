package com.projeto.barbearia.service;

import com.projeto.barbearia.controller.Dto.ServicoCreationDto;
import com.projeto.barbearia.entity.Agendamento;

import com.projeto.barbearia.entity.Servico;
import com.projeto.barbearia.entity.ServicoAgendado;
import com.projeto.barbearia.entity.roles.TiposServicos;
import com.projeto.barbearia.repository.AgendamentoRepository;
import com.projeto.barbearia.repository.ServicoAgendadoRepository;
import com.projeto.barbearia.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    private final ServicoRepository servicoRepository;

    @Autowired
    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> findAll() {
        return servicoRepository.findAll();
    }

    public Servico findByName(TiposServicos tiposServicos) {
        return servicoRepository.findByTiposServicos(tiposServicos).orElseThrow(() -> new RuntimeException("Servico não encontrado"));
    }

    @Transactional
    public Servico createServico(ServicoCreationDto servicoCreationDto) {
        boolean existe = servicoRepository
                .existsByTiposServicos(servicoCreationDto.tiposServicos());

        if (existe) {
            throw new RuntimeException("Serviço já existe");
        }

        Servico novoServico = new Servico(
                servicoCreationDto.tiposServicos(),
                servicoCreationDto.descricao(),
                servicoCreationDto.preco(),
                servicoCreationDto.duracaoMinutos()
        );

        return servicoRepository.save(novoServico);
    }


}
