package com.projeto.barbearia.entity;

import com.projeto.barbearia.entity.roles.StatusResgateRecompensa;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ResgateRecompensaFidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    private RecompensaFidelidade recompensa;

    @ManyToOne
    private Agendamento agendamento;

    @Column(nullable = false)
    private Integer pontosUtilizados;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusResgateRecompensa status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public RecompensaFidelidade getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(RecompensaFidelidade recompensa) {
        this.recompensa = recompensa;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Integer getPontosUtilizados() {
        return pontosUtilizados;
    }

    public void setPontosUtilizados(Integer pontosUtilizados) {
        this.pontosUtilizados = pontosUtilizados;
    }

    public StatusResgateRecompensa getStatus() {
        return status;
    }

    public void setStatus(StatusResgateRecompensa status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
