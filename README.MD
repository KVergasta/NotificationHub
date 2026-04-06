# 🚀 NotificationHub API

Uma API robusta e escalável desenvolvida em **Java 17** e **Spring Boot 3** para centralizar o envio de notificações através de múltiplos canais (E-mail, Push, SMS e WhatsApp).

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.x
* **Banco de Dados:** H2 (Em memória para desenvolvimento)
* **Persistência:** Spring Data JPA / Hibernate
* **Comunicação:** Spring Mail (SMTP/Gmail)
* **Produtividade:** Lombok
* **Ferramentas:** Maven, Jackson (JSON handling)

## 🏗️ Arquitetura e Design Patterns

O projeto utiliza o **Strategy Pattern** para permitir a extensibilidade de canais de comunicação sem alterar o núcleo da lógica de negócio.

* **BroadcastChannel (Interface):** Define o contrato para todos os canais de envio.
* **GeneratorNotification (Service):** O "cérebro" da aplicação que gerencia a lista de canais disponíveis e decide qual utilizar com base no tipo da notificação.
* **Custom Converters:** Implementação de `AttributeConverter` para mapear Enums complexos no banco de dados de forma simplificada (Ex: "E" para EMAIL).

## 🚀 Como Executar o Projeto

### Pré-requisitos
* JDK 17
* Maven 3.x

### Configuração do E-mail (Gmail)
Para enviar e-mails reais, você deve configurar as variáveis de ambiente ou editar o `src/main/resources/application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu-email@gmail.com
spring.mail.password=sua-senha-de-app-de-16-digitos