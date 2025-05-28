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
- **JUnit 5** + **Mockito** (testes)
- **JaCoCo** (cobertura de testes)
- **Maven**

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
└── dto/               # Objetos de transferência
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

## 👥 Autor

Desenvolvido por **Débora** - [GitHub](https://github.com/deborabeatriz-dev)
