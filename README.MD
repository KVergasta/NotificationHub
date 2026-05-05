# 🚀 NotificationHub API

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3.x
* **Banco de Dados:** PostegresSQL (Container)
* **Persistência:** Spring Data JPA / Hibernate
* **Comunicação:** Spring Mail (SMTP/Gmail)
* **Produtividade:** Lombok
* **Ferramentas:** Maven, Jackson (JSON handling), Docker ()


## 🏗️ Arquitetura e Design Patterns

O projeto utiliza o **Strategy Pattern** para permitir a extensibilidade de canais de comunicação sem alterar o núcleo da lógica de negócio.

* **BroadcastChannel (Interface):** Define o contrato para todos os canais de envio.
* **GeneratorNotification (Service):** O "cérebro" da aplicação que gerencia a lista de canais disponíveis e decide qual utilizar com base no tipo da notificação.
* **Custom Converters:** Implementação de `AttributeConverter` para mapear Enums complexos no banco de dados de forma simplificada (Ex: "E" para EMAIL).

## 🚀 Como Executar o Projeto

### Pré-requisitos
* JDK 17
* Maven 3.x



https://aiven.io/tools/kafka-visualization