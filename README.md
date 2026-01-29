# 💈 Barbearia API
Sistema backend para gerenciamento completo de uma barbearia, desenvolvido em Java com Spring Boot, focado em agendamentos, autenticação, serviços, pagamentos e controle de profissionais.
O projeto foi estruturado com arquitetura limpa, separação de responsabilidades e boas práticas de mercado, visando escalabilidade, segurança e fácil manutenção.

---

## 🚀 Funcionalidades

### 🔐 Autenticação e Segurança

- Login com autenticação
- JWT (JSON Web Token)
- Controle de acesso por roles
- Filtro de autenticação (jwtFilter)
- Configuração centralizada de segurança (SecurityConfig)

### 👤 Usuários

- Cadastro de usuários
- Autenticação
- Gerenciamento de perfil

### 💼 Profissionais

- Cadastro de profissionais
- Controle de disponibilidade
- Visualização de agenda
- Gestão de atendimentos

### 📅 Agendamentos

- Criação de agendamentos
- Associação de múltiplos serviços por agendamento
- Controle de status:
  - PENDENTE
  - CONFIRMADO
  - CANCELADO
  - CONCLUIDO
- Filtros por:
  - data
  - status
  - profissional
  - usuário
 
### ✂️ Serviços

- Cadastro de serviços
- Preço
- Duração
- Tipo de serviço
- Ativação/desativação

--- 

## 🧠 Modelagem Conceitual

- **Usuario** → cliente do sistema
- **Profissional** → barbeiro
- **Agendamento** → vínculo entre usuário e profissional
- **Servico** → serviços oferecidos
- **ServicoAgendado** → tabela intermediária (N:N)
- **Pagamento** → pagamento associado ao agendamento
- **ProfissionalDisponibilidade** → horários disponíveis do profissional

--- 

## ▶️ Como Executar o Projeto

### 📋 Pré-requisitos

- Java 17+
- Maven
- MySQL
- Conta Google (OAuth2)
- Conta Gmail (SMTP)

### 🗄️ Banco de Dados
Crie o banco no MySQL:
```plaintext
CREATE DATABASE barbeariadb;
```

### 🔐 Variáveis de Ambiente
Configure as seguintes variáveis de ambiente no sistema ou no .env:
```plaintext
SPRING_USERNAME=seu_usuario_mysql
SPRING_PASSWORD=sua_senha_mysql

JWT_SECRET=seu_segredo_jwt

GOOGLE_CLIENT_ID=seu_client_id_google
GOOGLE_CLIENT_SECRET=seu_client_secret_google

MAIL_USERNAME=seu_email@gmail.com
MAIL_PASSWORD=sua_senha_de_app_gmail
```

### ▶️ Executando a aplicação
```plaintext
mvn clean install
mvn spring-boot:run
```
Ou pela IDE:
- Execute a classe BarbeariaApplication

---

## 📜 Tecnologias Utilizadas  

### 🔹 Back-End  
- **Linguagem:** Java  
- **Framework:** Spring Boot  
- **Gerenciamento de Dependências:** Maven
- **Arquitetura:** API RESTful
- **Padrões:** DTOs, Services, Repositories, Modularização por entidade

### 🔹 Banco de Dados  
- **Banco:** MySQL
- **ORM:** Spring Data JPA
- **Migração/Schema:** Hibernate DDL Auto
- **Relacionamentos:** JPA (OneToMany, ManyToOne, ManyToMany, OneToOne)

### 🔹 Segurança  
- **Autenticação:** Spring Security  
- **Autenticação Stateless:** JWT (JSON Web Token)
- **Autenticação Social:** OAuth2 (Google Login)
- **Criptografia de Senhas:** BCrypt
- **Filtros de Segurança:** JWT Filter
- **Controle de Acesso:** Roles e Authorities

### 🔹Infraestrutura e Configuração 
- **Gerenciamento de Configurações:** Variáveis de Ambiente (.env)
- **Perfis de Ambiente:** application.properties
- **Email Service:** SMTP (Gmail)
- **Autenticação por Token:** API Security Token

### 🔹 Comunicação e Serviços
- **Envio de E-mails:** Spring Mail (SMTP)
- **Autenticação Externa:** OAuth2 Client (Google)
- **Serviços de Token:** JWT Service
- **Serialização:** Jackson (JSON)

### 🔹 Tratamento de Erros
- **Exception Handling Global:** @ControllerAdvice
- Exceções Customizadas por domínio
- **Padrão de resposta:** ExceptionResponse
- HTTP Status personalizados

### 🔹 Outras Bibliotecas e Utilitários   
- **Envio de E-mails:** Spring Mail  
- **Logs e Monitoramento:** Spring Boot Actuator

---

## 📌 Status do Projeto
🟢 Em desenvolvimento ativo

--- 

## 📄 Licença
Projeto pessoal para fins educacionais e portfólio.

--- 

## ✍ Autor
André Maurílio -
Estudante de TI | Back-end Developer 
