# 🚀 NotificationHub

O **NotificationHub** é uma solução robusta e escalável para centralizar, gerenciar e disparar notificações em tempo real. Utilizando uma arquitetura moderna orientada a eventos, o sistema é capaz de processar envios massivos de mensagens de forma assíncrona e resiliente, distribuindo os alertas através de múltiplos canais de comunicação.

---

## 🏗️ Arquitetura do Sistema

A aplicação foi desenhada seguindo os princípios de microsserviços e desacoplamento de componentes, garantindo alta disponibilidade:

* **Frontend (Angular 12):** Painel administrativo e de controle para digitação de mensagens, monitoramento de saúde dos canais e exibição de relatórios históricos de envio.
* **Mensageria (Apache Kafka):** Atua como o coração do sistema distribuído. Recebe as intenções de notificação nas filas principais e gerencia tópicos de recuperação (*Retry e Dead Letter Queue*) para evitar perda de dados.
* **Backend (Spring Boot 3.4):** Processador core que consome os tópicos do Kafka de forma assíncrona, valida os payloads e aciona os provedores físicos de entrega.
* **Banco de Dados (PostgreSQL):** Persistência de dados das entidades, histórico detalhado de auditoria e controle de estados dos envios.

---

## 🛠️ Tecnologias Utilizadas

### Backend & Infraestrutura

* **Linguagem Principal:** Java 21
* **Core Framework:** Spring Boot 3.4.0
* **Mensageria & Eventos:** Apache Kafka
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL (Ambiente em Container)
* **Envio de E-mail:** Spring Mail (Integração SMTP / Gmail)
* **Ambiente de Dev:** Docker & Docker Compose
* **Produtividade & Utilitários:** Lombok, Jackson JSON, Maven

### Frontend

* **Framework:** Angular 12
* **Estilização:** CSS Customizado (Dark Mode Integrado)
* **Comunicação:** Angular HTTP Client (Integração REST)
* **Notificações Nativas:** `@angular/pwa` (Service Workers para Web Push)
* **Feedback Visual:** `ngx-toastr` / `SweetAlert2`

---

## 🧠 Design Patterns & Boas Práticas

* **Strategy Pattern:** Utilizado na camada de serviços do Spring Boot através da interface `BroadcastChannel`. Isso permite adicionar novos canais de comunicação (SMS, WhatsApp, Telegram) de forma plugável, sem modificar as regras de negócio existentes.
* **Mecanismo de Polling Resiliente:** Os consumidores do Kafka são protegidos por tratamento rigoroso de exceções (`Throwable`), desviando payloads corrompidos ou falhas externas de infraestrutura para tópicos de reprocessamento agendado.
* **Custom Converters (JPA):** Implementação de `AttributeConverter` para mapear Enums de domínio diretamente para caracteres otimizados no banco de dados (ex: converter o Enum `ChannelType.EMAIL` em uma única flag `"E"`), economizando armazenamento e melhorando a indexação do banco.
* **Segurança VAPID:** Uso do padrão voluntário de identificação de servidor de aplicação (VAPID) para assinar as requisições de Push Notifications, garantindo que apenas o backend legítimo consiga enviar alertas para o navegador do cliente.

---

## 🚀 Como Executar o Projeto

### 📋 Pré-requisitos

* Java JDK 17 ou superior
* Node.js (v12 ou v16 compatível com a CLI do Angular)
* Maven 3.x
* Docker & Docker Compose instalados na máquina

---

### 📦 1. Subindo a Infraestrutura (Docker)

O projeto depende do banco de dados e do sistema de mensageria para funcionar. Certifique-se de que o Docker está rodando e execute o comando abaixo na raiz do diretório de infraestrutura:

```bash
docker compose up -d

```

> Isso iniciará os serviços do **PostgreSQL**, **Zookeeper** e **Apache Kafka**.

---

### ☕ 2. Executando o Backend (Spring Boot)

1. Navegue até a pasta do projeto backend (`NotificationServ`).
2. Configure as variáveis de chaves VAPID geradas e suas credenciais SMTP no arquivo `src/main/resources/application.properties`.
3. Compile e rode o projeto utilizando o Maven:

```bash
mvn clean install
mvn spring-boot:run

```

O servidor backend subirá por padrão no endereço `http://localhost:8000`.

---

### 🅰️ 3. Executando o Frontend (Angular)

1. Navegue até a pasta do aplicativo frontend (`NotificationHub_APP`).
2. Instale as dependências locais correspondentes ao ecossistema do projeto:

```bash
npm install

```

3. Inicie o servidor de desenvolvimento local do Angular:

```bash
ng serve

```

Abra o navegador e acesse a aplicação em `http://localhost:4200` (ou na porta correspondente configurada no seu ambiente).

---

## 📊 Visualização de Fluxos do Kafka

Para monitorar visualmente o tráfego das mensagens dentro dos tópicos como `notification-stream-mail`, `notification-stream-push` e `notification-retry`, você pode utilizar ferramentas interativas de fluxo de dados.

* [Aiven Kafka Visualization Tool](https://aiven.io/tools/kafka-visualization) — Utilize para mapear e entender a topologia dos seus tópicos e a jornada que a notificação faz desde o clique no botão do formulário até a gravação final do status no PostgreSQL.