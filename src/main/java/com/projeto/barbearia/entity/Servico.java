package com.projeto.barbearia.entity;

import com.projeto.barbearia.entity.roles.TiposServicos;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TiposServicos tiposServicos;

    private String descricao;
    private Double preco;
    private Integer duracaoMinutos;


    private Boolean ativo = true;

    @OneToMany(mappedBy = "servico")
    private List<ServicoAgendado> servicoAgendadoList;


    public Servico() {}

    public Servico(TiposServicos tiposServicos, String descricao, Double preco, Integer duracaoMinutos) {
        this.tiposServicos = tiposServicos;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoMinutos = duracaoMinutos;
        this.ativo = true;
    }



    public Long getId() {
        return id;
    }

    public TiposServicos getTiposServicos() {
        return tiposServicos;
    }

    public void setServico(TiposServicos tiposServicos) {
        this.tiposServicos = tiposServicos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
