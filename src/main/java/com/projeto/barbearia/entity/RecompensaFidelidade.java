package com.projeto.barbearia.entity;

import com.projeto.barbearia.entity.roles.TipoRecompensaFidelidade;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class RecompensaFidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @Column(nullable = false)
    private Integer pontosNecessarios;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRecompensaFidelidade tipo;

    private BigDecimal valorDesconto;

    private Boolean ativa = true;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public RecompensaFidelidade(String nome, String descricao, Integer pontosNecessarios, TipoRecompensaFidelidade tipo, BigDecimal valorDesconto) {
        this.nome = nome;
        this.descricao = descricao;
        this.pontosNecessarios = pontosNecessarios;
        this.tipo = tipo;
        this.valorDesconto = valorDesconto;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getPontosNecessarios() {
        return pontosNecessarios;
    }

    public void setPontosNecessarios(Integer pontosNecessarios) {
        this.pontosNecessarios = pontosNecessarios;
    }

    public TipoRecompensaFidelidade getTipo() {
        return tipo;
    }

    public void setTipo(TipoRecompensaFidelidade tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

}
