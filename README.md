# Barbearia API

Backend REST para uma plataforma de barbearia, desenvolvido com Java e Spring Boot. O projeto centraliza funcionalidades de autenticacao, agendamento inteligente, gestao de servicos, profissionais, pagamentos, lembretes e fidelizacao de clientes.

A proposta e ir alem de um CRUD tradicional, oferecendo regras de negocio proximas de um sistema real de atendimento: horarios dinamicos por disponibilidade do barbeiro, controle de status do atendimento, pontos de fidelidade e lembretes automaticos.

## Funcionalidades

### Autenticacao e seguranca

- Login tradicional com email e senha.
- Autenticacao via JWT.
- Login social com Google OAuth2.
- Controle de acesso com Spring Security.
- Criptografia de senha com BCrypt.
- Filtro JWT para protecao das rotas autenticadas.

### Usuarios e clientes

- Cadastro e gerenciamento de usuarios.
- Perfil de cliente com telefone, email, pontos de fidelidade e nivel.
- Suporte a usuarios criados por login Google.

### Profissionais

- Cadastro de barbeiros/profissionais.
- Vinculo de disponibilidade por dia da semana.
- Consulta de agenda por profissional, data e status.

### Servicos

- Cadastro de servicos oferecidos pela barbearia.
- Servicos com tipo, descricao, preco, duracao e status ativo.
- Suporte a multiplos servicos no mesmo agendamento.

Tipos de servico previstos:

- Corte
- Barba
- Sobrancelha
- Lavagem
- Hidratacao

### Agendamento inteligente

- Criacao de agendamentos com cliente, profissional, data, horario e servicos.
- Calculo de valor total com base nos servicos selecionados.
- Geracao de horarios disponiveis em blocos de 15 minutos.
- Validacao de disponibilidade considerando:
  - agenda do profissional;
  - dia da semana;
  - horario de inicio e fim;
  - duracao total dos servicos;
  - margem tecnica entre atendimentos;
  - conflitos com agendamentos existentes.
- Validacao no backend para impedir agendamentos em horarios indisponiveis.

### Fluxo de status do atendimento

O agendamento possui um fluxo de status para representar melhor a jornada do cliente:

```text
PENDENTE -> CONFIRMADO -> EM_ATENDIMENTO -> CONCLUIDO
                     \-> CANCELADO
```

Principais status:

- `PENDENTE`: agendamento criado, ainda sem confirmacao.
- `CONFIRMADO`: pagamento ou confirmacao realizada.
- `EM_ATENDIMENTO`: atendimento iniciado pelo barbeiro/admin.
- `CONCLUIDO`: atendimento finalizado.
- `CANCELADO`: agendamento cancelado.

### Pagamentos

- Controle de pagamentos associados ao agendamento.
- Suporte a pagamento online e presencial.
- Confirmacao de agendamento a partir do pagamento.
- Estrutura preparada para evoluir com gateway de pagamento.

### Lembretes automaticos

- Servico agendado para envio de lembretes por email.
- Lembretes antes do atendimento:
  - 24 horas antes;
  - 1 hora antes.
- Controle para evitar envio duplicado do mesmo lembrete.

### Sistema de fidelidade

- Pontos de fidelidade vinculados ao usuario.
- Historico de movimentacoes de pontos.
- Pontuacao baseada em regras de negocio:
  - pontos por agendamento concluido;
  - bonus por pagamento online;
  - bonus por aniversario;
  - bonus por indicacao;
  - pontos equivalentes ao valor dos servicos;
  - pontos em dobro em dias estrategicos.
- Niveis de fidelidade:
  - Bronze: 0 a 199 pontos ganhos;
  - Prata: 200 a 499 pontos ganhos;
  - Ouro: 500 a 999 pontos ganhos;
  - Diamante: 1000+ pontos ganhos.
- Estrutura para recompensas e resgates.

## Modelagem principal

Principais entidades do dominio:

- `Usuario`: cliente do sistema.
- `Profissional`: barbeiro/profissional responsavel pelos atendimentos.
- `ProfissionalDisponibilidade`: dias e horarios disponiveis de cada profissional.
- `Servico`: servicos oferecidos pela barbearia.
- `Agendamento`: reserva de horario entre cliente e profissional.
- `ServicoAgendado`: associacao entre agendamento e servicos selecionados.
- `Pagamento`: pagamento vinculado ao agendamento.
- `MovimentacaoFidelidade`: historico de entrada e saida de pontos.
- `RecompensaFidelidade`: catalogo de recompensas disponiveis.
- `ResgateRecompensaFidelidade`: registro de recompensas resgatadas pelo cliente.

## Tecnologias utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- OAuth2 Client
- JWT
- BCrypt
- Maven
- MySQL
- Hibernate
- Spring Mail
- Bean Validation

## Arquitetura

O projeto segue uma organizacao em camadas:

```text
controller -> service -> repository -> entity
```

Tambem utiliza:

- DTOs para entrada e saida de dados.
- Enums para status e tipos do dominio.
- Exceptions customizadas.
- Repositories com Spring Data JPA.
- Services responsaveis pelas regras de negocio.
- Configuracao centralizada de seguranca.

## Exemplos de endpoints

### Autenticacao

```http
POST /auth/login
GET /oauth2/authorization/google
```

### Agendamentos

```http
GET /agendamentos
POST /agendamentos
GET /agendamentos/horarios-disponiveis?profissionalId=1&data=2026-06-20&servicosIds=1,2
PUT /agendamentos/{agendamentoId}/cancelar-agendamento
PATCH /agendamentos/{agendamentoId}/iniciar
PATCH /agendamentos/{agendamentoId}/concluir
```

### Fluxo de agendamento

1. Cliente seleciona servicos.
2. Cliente escolhe profissional.
3. Backend calcula horarios disponiveis.
4. Cliente seleciona data e horario.
5. Backend valida disponibilidade novamente.
6. Agendamento e criado como `PENDENTE`.
7. Pagamento confirma o agendamento.
8. Barbeiro inicia o atendimento.
9. Barbeiro conclui o atendimento.
10. Sistema credita pontos de fidelidade.

## Como executar

### Pre-requisitos

- Java 17+
- Maven
- MySQL
- Conta Google Cloud para OAuth2
- Conta Gmail ou SMTP configurado para envio de emails

### Banco de dados

Crie o banco no MySQL:

```sql
CREATE DATABASE barbeariadb;
```

### Variaveis de ambiente

Configure as variaveis usadas pela aplicacao:

```env
SPRING_USERNAME=seu_usuario_mysql
SPRING_PASSWORD=sua_senha_mysql

JWT_SECRET=seu_segredo_jwt

GOOGLE_CLIENT_ID=seu_client_id_google
GOOGLE_CLIENT_SECRET=seu_client_secret_google

MAIL_USERNAME=seu_email@gmail.com
MAIL_PASSWORD=sua_senha_de_app_gmail
```

### Executando com Maven

```bash
mvn clean install
mvn spring-boot:run
```

Ou execute a classe principal da aplicacao pela IDE.

## Possiveis evolucoes

- Frontend PWA mobile para clientes.
- Painel do barbeiro com fila de atendimentos.
- Dashboard para o dono da barbearia.
- Relatorios de faturamento, ocupacao e fidelidade.
- Avaliacao pos-atendimento.
- Lista de espera automatica.
- Reagendamento inteligente.
- Encaixes inteligentes em horarios livres.
- Integracao com gateway de pagamento.
- Envio de notificacoes por WhatsApp.

## Status

Projeto em desenvolvimento ativo.

## Autor

Andre Maurilio  
Estudante de TI | Desenvolvedor Back-end
