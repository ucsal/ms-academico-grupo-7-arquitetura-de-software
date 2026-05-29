# ms-academico

Microservico academico criado com Java 17, Spring Boot e Maven para gerenciar
escolas, cursos, matrizes curriculares e disciplinas.

## Stack

- Java 17
- Spring Boot 3.3.x
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL / Neon
- Lombok
- Bean Validation
- Springdoc OpenAPI / Swagger UI
- Eureka Client

## Funcionalidades

- CRUD basico de escolas, cursos, matrizes e disciplinas
- Validacao de entrada com Jakarta Bean Validation
- Respostas padronizadas com mensagem, timestamp e dados
- Paginacao nos endpoints de listagem
- Geracao automatica de tabelas pelo Hibernate em ambiente local/desenvolvimento
- Documentacao interativa via Swagger

## Requisitos

- JDK 17
- Maven 3.9+
- Banco PostgreSQL acessivel, como Neon

## Integração com API Gateway

Este microsserviço está **totalmente integrado** com o API Gateway do grupo. Todos os endpoints utilizam o prefixo `/api` para compatibilidade com o roteamento do Gateway.

### Configuração de Portas

| Serviço | Porta | Descrição |
|---------|-------|-----------|
| **API Gateway** | 8080 | Porta pública (entrada) |
| **ms-academico** | 8082 | Porta interna (rotas protegidas) |
| **Auth Service** | 8081 | Autenticação (referência) |

### Acesso via API Gateway

Todas as requisições **protegidas** devem ir através do Gateway:

```bash
# Via Gateway (com JWT válido)
curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8080/api/cursos

curl -H "Authorization: Bearer TOKEN" \
  http://localhost:8080/api/escolas
```

### Acesso Direto (Desenvolvimento Local)

Para testes locais **sem** passar pelo Gateway:

```bash
# Acesso direto (sem autenticação)
curl http://localhost:8082/api/cursos
curl http://localhost:8082/api/escolas
```

### Fluxo de Requisição

```
Cliente
   ↓
[API Gateway - porta 8080]
   ↓ (valida JWT via JwtAuthFilter)
   ↓
[ms-academico - porta 8082]
   ↓
Retorna JSON protegido
```

### Endpoints Disponíveis

Todos os endpoints agora utilizam o prefixo `/api`:

| Recurso | Método | Rota | Via Gateway |
|---------|--------|------|------------|
| Escolas | POST | `/api/escolas` | `http://localhost:8080/api/escolas` |
| Escolas | GET | `/api/escolas` | `http://localhost:8080/api/escolas` |
| Escolas | GET | `/api/escolas/{id}` | `http://localhost:8080/api/escolas/{id}` |
| Escolas | DELETE | `/api/escolas/{id}` | `http://localhost:8080/api/escolas/{id}` |
| Cursos | POST | `/api/cursos` | `http://localhost:8080/api/cursos` |
| Cursos | GET | `/api/cursos` | `http://localhost:8080/api/cursos` |
| Cursos | GET | `/api/cursos/{id}` | `http://localhost:8080/api/cursos/{id}` |
| Cursos | DELETE | `/api/cursos/{id}` | `http://localhost:8080/api/cursos/{id}` |
| Matrizes | POST | `/api/matrizes` | `http://localhost:8080/api/matrizes` |
| Matrizes | GET | `/api/matrizes` | `http://localhost:8080/api/matrizes` |
| Matrizes | GET | `/api/matrizes/{id}` | `http://localhost:8080/api/matrizes/{id}` |
| Matrizes | DELETE | `/api/matrizes/{id}` | `http://localhost:8080/api/matrizes/{id}` |
| Disciplinas | POST | `/api/disciplinas` | `http://localhost:8080/api/disciplinas` |
| Disciplinas | GET | `/api/disciplinas` | `http://localhost:8080/api/disciplinas` |
| Disciplinas | GET | `/api/disciplinas/{id}` | `http://localhost:8080/api/disciplinas/{id}` |
| Disciplinas | DELETE | `/api/disciplinas/{id}` | `http://localhost:8080/api/disciplinas/{id}` |

### Headers Obrigatórios

Ao acessar via API Gateway, **sempre inclua** o header JWT:

```bash
curl -X GET "http://localhost:8080/api/cursos" \
  -H "Authorization: Bearer seu_token_jwt_aqui"
```

### Segurança

- ✅ **JWT validado no Gateway** antes de chegar em ms-academico
- ✅ **Autenticação centralizada** no `JwtAuthFilter` do Gateway
- ✅ **Sem duplicação de validação** de tokens
- ✅ **Autorização de negócio** pode ser implementada em cada microsserviço se necessário

## Integração com API Gateway

A aplicação utiliza variáveis de ambiente para configuração dinâmica. Crie um arquivo `.env` na raiz do projeto copiando o modelo disponível em `.env.example`:

```bash
cp .env.example .env
```

Edite o `.env` com suas configurações locais:

```properties
# Banco de Dados (PostgreSQL)
DATABASE_URL=jdbc:postgresql://localhost:5432/ms_academico
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

# Servidor Web
SERVER_PORT=8082

# JPA/Hibernate (create, update, validate, none)
JPA_DDL_AUTO=update

# Eureka Service Discovery (desativado por padrão)
EUREKA_CLIENT_REGISTER_WITH_EUREKA=false
EUREKA_CLIENT_FETCH_REGISTRY=false
EUREKA_SERVER_URL=http://localhost:8761/eureka
```

**Importante:** O arquivo `.env` está no `.gitignore` e nunca deve ser commitado.

### Alterar a Porta

Para usar uma porta diferente de `8081`, altere a variável `SERVER_PORT` no arquivo `.env`:

```properties
SERVER_PORT=9001
```

A aplicação usará a nova porta na próxima inicialização.

### Configurar Eureka (Futuro)

Quando integrar com Eureka Service Discovery, ative o registro no arquivo `.env`:

```properties
EUREKA_CLIENT_REGISTER_WITH_EUREKA=true
EUREKA_CLIENT_FETCH_REGISTRY=true
EUREKA_SERVER_URL=http://seu-eureka-server:8761/eureka
```

**Nota:** Por padrão, o Eureka está desativado para funcionar sem dependências externas em ambiente local.

### Acessar a Documentação Swagger

Após iniciar a aplicação, acesse a documentação interativa:

```
http://localhost:8081/swagger-ui.html
```

## Banco de Dados

Por padrao, `JPA_DDL_AUTO` usa `update`, permitindo que o Hibernate crie ou atualize
automaticamente as tabelas mapeadas.

As tabelas criadas no PostgreSQL usam os nomes definidos nas entidades:

```sql
SELECT * FROM escolas;
SELECT * FROM cursos;
SELECT * FROM matrizes;
SELECT * FROM disciplinas;
```

Em ambientes controlados por migrations, altere `JPA_DDL_AUTO` para `validate` ou
`none`.

## Executando Localmente

### 1. Clonar o repositório

```bash
git clone https://github.com/ucsal/ms-academico.git
cd ms-academico
```

### 2. Configurar variáveis de ambiente

```bash
cp .env.example .env
```

Edite o `.env` com suas credenciais do banco de dados e configurações locais.

### 3. Iniciar a aplicação

```bash
mvn clean spring-boot:run
```

A aplicação iniciará na porta definida em `SERVER_PORT` (padrão: `8082`).

### 4. Verificar se está funcionando

```bash
# Acessar Swagger UI
http://localhost:8082/swagger-ui.html

# Listar escolas (esperado: lista vazia ou escolas criadas)
curl http://localhost:8082/api/escolas
```

Ou abra seu navegador em:
- **API REST (Direct)**: `http://localhost:8082/api/...`
- **Swagger UI**: `http://localhost:8082/swagger-ui.html`
- **Via API Gateway**: `http://localhost:8080/api/...` (com JWT)

## Endpoints

Todos os endpoints retornam JSON. **Todos utilizam o prefixo `/api`** para compatibilidade com o API Gateway.

| Recurso | Metodo | Caminho | Descricao |
| --- | --- | --- | --- |
| Escolas | `POST` | `/api/escolas` | Cria uma escola |
| Escolas | `GET` | `/api/escolas` | Lista escolas |
| Escolas | `GET` | `/api/escolas/{id}` | Busca uma escola |
| Escolas | `DELETE` | `/api/escolas/{id}` | Remove uma escola |
| Cursos | `POST` | `/api/cursos` | Cria um curso |
| Cursos | `GET` | `/api/cursos` | Lista cursos |
| Cursos | `GET` | `/api/cursos/{id}` | Busca um curso |
| Cursos | `DELETE` | `/api/cursos/{id}` | Remove um curso |
| Matrizes | `POST` | `/api/matrizes` | Cria uma matriz |
| Matrizes | `GET` | `/api/matrizes` | Lista matrizes |
| Matrizes | `GET` | `/api/matrizes/{id}` | Busca uma matriz |
| Matrizes | `DELETE` | `/api/matrizes/{id}` | Remove uma matriz |
| Disciplinas | `POST` | `/api/disciplinas` | Cria uma disciplina |
| Disciplinas | `GET` | `/api/disciplinas` | Lista disciplinas |
| Disciplinas | `GET` | `/api/disciplinas/{id}` | Busca uma disciplina |
| Disciplinas | `DELETE` | `/api/disciplinas/{id}` | Remove uma disciplina |
| Cursos | `GET` | `/cursos` | Lista cursos |
| Cursos | `GET` | `/cursos/{id}` | Busca um curso |
| Cursos | `DELETE` | `/cursos/{id}` | Remove um curso |
| Matrizes | `POST` | `/matrizes` | Cria uma matriz |
| Matrizes | `GET` | `/matrizes` | Lista matrizes |
| Matrizes | `GET` | `/matrizes/{id}` | Busca uma matriz |
| Matrizes | `DELETE` | `/matrizes/{id}` | Remove uma matriz |
| Disciplinas | `POST` | `/disciplinas` | Cria uma disciplina |
| Disciplinas | `GET` | `/disciplinas` | Lista disciplinas |
| Disciplinas | `GET` | `/disciplinas/{id}` | Busca uma disciplina |
| Disciplinas | `DELETE` | `/disciplinas/{id}` | Remove uma disciplina |

## Exemplos de uso

Criar escola:

```bash
curl -X POST http://localhost:8082/api/escolas \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Escola de Tecnologia\"}"
```

Criar curso:

```bash
curl -X POST http://localhost:8082/api/cursos \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Analise e Desenvolvimento de Sistemas\",\"escolaId\":1}"
```

Criar matriz:

```bash
curl -X POST http://localhost:8082/api/matrizes \
  -H "Content-Type: application/json" \
  -d "{\"codigo\":\"ADS-2026\",\"cursoId\":1}"
```

Criar disciplina:

```bash
curl -X POST http://localhost:8082/api/disciplinas \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Programacao Web\",\"cargaHoraria\":80,\"matrizId\":1}"
```

Listar com paginacao:

```bash
curl "http://localhost:8082/api/escolas?page=0&size=10&sort=id,asc"
```

**Via API Gateway (com JWT):**

```bash
curl -X GET "http://localhost:8080/api/escolas" \
  -H "Authorization: Bearer seu_token_jwt_aqui"
```

## Teste Rápido

Após iniciar a aplicação:

1. Abra [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)
2. Clique em `POST /api/escolas` e crie uma escola com: `{"nome":"Escola de Teste"}`
3. Verifique o banco de dados:

```sql
SELECT * FROM escolas;
```

## Estrutura do Projeto

```
ms-academico/
├── src/main/java/br/com/msacademico/
│   ├── MsAcademicoApplication.java      # Classe principal do Spring Boot
│   ├── config/                          # Configurações (OpenAPI/Swagger)
│   ├── controller/                      # REST Controllers
│   ├── dto/                             # Data Transfer Objects
│   ├── exception/                       # Manipulação de exceções
│   ├── model/                           # Entidades JPA
│   ├── repository/                      # Acesso a dados (JPA)
│   └── service/                         # Lógica de negócio
├── src/main/resources/
│   └── application.yml                  # Configuração da aplicação
├── pom.xml                              # Dependências Maven
├── .env.example                         # Exemplo de variáveis de ambiente
└── README.md                            # Este arquivo
```

## Observações

- Nunca versione arquivo `.env` com credenciais reais.
- `JPA_DDL_AUTO=update` é prático para desenvolvimento; use migrations (Flyway/Liquibase) em produção.
- O Eureka Client está configurado como dependência do pom.xml e pode ser ativado via variáveis de ambiente quando necessário integrar com Eureka Server.
- A aplicação funciona normalmente sem Eureka ativo em ambiente local.
- Para integração futura com API Gateway, configure `EUREKA_CLIENT_REGISTER_WITH_EUREKA=true` e `EUREKA_SERVER_URL` corretamente.

