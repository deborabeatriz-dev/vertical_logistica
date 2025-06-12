# Vertical Logística 📦

Sistema de processamento de dados legados, desenvolvido com Spring Boot.

## 📋 Sobre o Projeto

O **Vertical Logística** é uma aplicação REST API que processa arquivos de sistemas legados para extrair e organizar informações de usuários, pedidos e produtos. O sistema oferece funcionalidades para upload de arquivos, processamento de dados em formato fixo e consultas filtradas.

## 🚀 Funcionalidades

- **Upload e Processamento de Arquivos**: Processa arquivos de sistemas legados com formato fixo
- **Gestão de Usuários**: Criação e consulta de usuários com seus pedidos
- **Controle de Pedidos**: Organização de pedidos por usuário com produtos associados
- **Filtros Avançados**: Consultas por ID do pedido e intervalo de datas
- **Tratamento de Erros**: Relatórios detalhados de linhas com problemas no processamento

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **H2 Database** (desenvolvimento)
- **Lombok**
- **OpenAPI 3 + Swagger UI** (documentação da API)
- **JUnit 5** + **Mockito** (testes)
- **JaCoCo** (cobertura de testes)
- **Maven**

## 🧱 Decisões Arquiteturais e Padrões Aplicados

- **Arquitetura em Camadas (Layered Architecture)**: A aplicação foi estruturada em camadas (controller, service, repository), promovendo separação de responsabilidades e facilitando manutenção e testes.

- **Padrão DTO (Data Transfer Object)**: Utilizado para abstrair e controlar os dados trafegados entre as camadas da aplicação e a exposição via API.

- **Repository Pattern (Spring Data JPA)**: Uso de interfaces que abstraem a persistência de dados, facilitando integração com o banco e promovendo testes mais limpos.

- **Service Layer Pattern**: A lógica de negócio foi encapsulada em serviços dedicados (UserService, OrderUserService, ProductOrderService, etc.), permitindo reutilização e testes unitários eficazes.

- **Tratamento de Erros e Relatórios Detalhados**: Erros no processamento de arquivos são capturados e retornados de forma estruturada, mantendo a robustez e a rastreabilidade.

- **Banco H2 para Desenvolvimento/Testes**: O H2 foi escolhido por ser leve, embutido e de fácil configuração, ideal para testes locais.

- **Testes Automatizados com JUnit 5 + Mockito + JaCoCo**: Utilizados para garantir a qualidade e cobertura do código, validando tanto os fluxos de sucesso quanto de erro.

## 📁 Estrutura do Projeto

```
src/main/java/com/dev/vertical_logistica/
├── controller/          # Controladores REST
│   ├── ParseController.java
│   └── UserController.java
├── service/            # Lógica de negócio
│   ├── ParseSystemLegacyService.java
│   ├── UserService.java
│   ├── OrderUserService.java
│   └── ProductOrderService.java
├── model/              # Entidades JPA
├── repository/         # Repositórios de dados
├── dto/               # Objetos de transferência
└── config/            # Configurações
```

## 🔧 Pré-requisitos

- **Java 21** ou superior
- **Maven 3.6+**
- **Git**

## 🚀 Como Executar

### 1. Clone o repositório
```bash
git clone https://github.com/deborabeatriz-dev/vertical_logistica.git
cd vertical_logistica
```

### 2. Execute os testes
```bash
mvn clean test
```

### 3. Execute a aplicação
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📡 Endpoints da API

### Upload de Arquivo
```http
POST /api/parse/upload
Content-Type: multipart/form-data

Parâmetros:
- file: arquivo de sistema legado (.txt)
```

**Exemplo de resposta:**
```json
{
    "totalLinesProcessed": 23,
    "totalLinesWithErrors": 0,
    "lineErrors": []
}
```

### Consultar Usuários
```http
GET /api/users/getUsers

Parâmetros opcionais:
- orderId: ID do pedido (Long)
- startDate: Data inicial (yyyy-MM-dd)
- endDate: Data final (yyyy-MM-dd)
```

**Exemplo de resposta:**
```json
[
  {
        "user_id": 199,
        "name": "Miss Terry Boyle",
        "orders": [
            {
                "order_id": 1829,
                "total": 1263.59,
                "date": "2021-06-25",
                "products": [
                    {
                        "product_id": 3,
                        "value": 1263.59
                    }
                ]
            }
        ]
    }
]
```

## 📄 Formato do Arquivo Legado

O sistema processa arquivos de texto com formato fixo:

```
Posição  | Tamanho | Descrição
---------|---------|------------------
0-9      | 10      | ID do Usuário
10-54    | 45      | Nome do Usuário
55-64    | 10      | ID do Pedido
65-74    | 10      | ID do Produto
75-86    | 12      | Preço do Produto
87-94    | 8       | Data (yyyyMMdd)
```

**Exemplo de linha:**
```
0000000070                              Dev Teste00000000000007530000000003     1836.7420210308
```

## 🧪 Executar Testes

```bash
# Executar todos os testes
mvn test

# Executar testes com relatório de cobertura
mvn clean test jacoco:report

# Ver relatório de cobertura
open target/site/jacoco/index.html
```

## 📊 Cobertura de Testes

O projeto utiliza JaCoCo para monitoramento da cobertura de testes. Os relatórios são gerados em `target/site/jacoco/`.

**Classes testadas:**
- ✅ Controllers (ParseController, UserController)
- ✅ Services (ParseSystemLegacyService, UserService, OrderUserService, ProductOrderService)
- ✅ Cenários de sucesso e falha
- ✅ Validações de dados

## 📝 Logging

O sistema utiliza SLF4J com níveis configuráveis:
- **INFO**: Processamento normal de dados
- **DEBUG**: Detalhes de linhas processadas
- **ERROR**: Erros de processamento e exceções


## ⚙️ Configuração do Banco de Dados

O projeto utiliza **H2 Database** para armazenamento dos dados durante o desenvolvimento e testes.

### Modos de uso do banco H2

Você pode escolher entre duas configurações no arquivo `application.properties` para o banco de dados H2:

- **Banco em memória (padrão para testes):**

```properties
spring.datasource.url=jdbc:h2:mem:tempdb
#spring.datasource.url=jdbc:h2:file:./data/tempdb
spring.datasource.username=admin
spring.datasource.password=
server.port=0
#server.port=8080
```

- **Banco persistente em arquivo:**

```properties
#spring.datasource.url=jdbc:h2:mem:tempdb
spring.datasource.url=jdbc:h2:file:./data/tempdb
spring.datasource.username=admin
spring.datasource.password=
#server.port=0
server.port=8080
```

### 🖥️ Acessando o H2 Console

A aplicação disponibiliza um console web para visualizar o banco H2.

1. Inicie a aplicação localmente.
2. Acesse no navegador:

O banco estará disponível em: `http://localhost:8080/h2-console`

> 💡 Se estiver usando o banco em memória com `server.port=0`, a aplicação será iniciada em uma porta aleatória. Verifique no terminal a porta atribuída e use-a no lugar da 8080.

3. Preencha os campos do formulário com os dados corretos, dependendo da configuração utilizada:

#### Para banco em memória:
- **JDBC URL**: `jdbc:h2:mem:tempdb`

#### Para banco persistente em arquivo:
- **JDBC URL**: `jdbc:h2:file:./data/tempdb`

**Usuário**: `admin`  
**Senha**: *(deixe em branco)*

4. Clique em **Connect**

### 📚 Documentação da API (Swagger)

O projeto inclui documentação automática da API utilizando OpenAPI 3 com Swagger UI.

#### Acessando a Documentação
Com a aplicação em execução, acesse:

Swagger UI: http://localhost:8080/swagger-ui.html

> 💡 Se estiver usando server.port=0, substitua 8080 pela porta atribuída automaticamente.

## Observações importantes

- Nos testes automatizados, a aplicação utiliza a configuração com banco em memória e porta de servidor aleatória (server.port=0), para evitar conflitos.

- Ao rodar a aplicação localmente para desenvolvimento, recomenda-se usar o banco persistente em arquivo para facilitar a depuração e análise dos dados.

## 👥 Autor

Desenvolvido por **Débora Beatriz** - [GitHub](https://github.com/deborabeatriz-dev) | [LinkedIn](https://www.linkedin.com/in/deborabeatriz/)
