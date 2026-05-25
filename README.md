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

## Configuracao

A aplicacao sobe na porta `8081` e usa o nome `ms-academico`.

Crie um arquivo `.env` na raiz do projeto com as variaveis abaixo:

```properties
DATABASE_URL=jdbc:postgresql://<host-neon>/<database>?sslmode=require
DATABASE_USERNAME=<usuario>
DATABASE_PASSWORD=<senha>
JPA_DDL_AUTO=update
EUREKA_CLIENT_REGISTER_WITH_EUREKA=false
EUREKA_CLIENT_FETCH_REGISTRY=false
```

O arquivo `.env` e carregado automaticamente pela aplicacao local por causa desta
configuracao em `application.yml`:

```yaml
spring:
  config:
    import: optional:file:.env[.properties]
```

Importante: o `.env` esta no `.gitignore` e nao deve ser commitado.

### Neon

Se o Neon fornecer uma connection string neste formato:

```text
postgresql://usuario:senha@host/database?sslmode=require&channel_binding=require
```

Converta para o formato JDBC usado pelo Spring:

```properties
DATABASE_URL=jdbc:postgresql://host/database?sslmode=require&channelBinding=require
DATABASE_USERNAME=usuario
DATABASE_PASSWORD=senha
```

## Banco de dados

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

## Executando localmente

```bash
mvn clean spring-boot:run
```

Apos iniciar, acesse:

- API: `http://localhost:8081`
- Swagger UI: `http://localhost:8081/swagger-ui.html`

## Endpoints

Todos os endpoints retornam JSON.

| Recurso | Metodo | Caminho | Descricao |
| --- | --- | --- | --- |
| Escolas | `POST` | `/escolas` | Cria uma escola |
| Escolas | `GET` | `/escolas` | Lista escolas |
| Escolas | `GET` | `/escolas/{id}` | Busca uma escola |
| Escolas | `DELETE` | `/escolas/{id}` | Remove uma escola |
| Cursos | `POST` | `/cursos` | Cria um curso |
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
curl -X POST http://localhost:8081/escolas \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Escola de Tecnologia\"}"
```

Criar curso:

```bash
curl -X POST http://localhost:8081/cursos \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Analise e Desenvolvimento de Sistemas\",\"escolaId\":1}"
```

Criar matriz:

```bash
curl -X POST http://localhost:8081/matrizes \
  -H "Content-Type: application/json" \
  -d "{\"codigo\":\"ADS-2026\",\"cursoId\":1}"
```

Criar disciplina:

```bash
curl -X POST http://localhost:8081/disciplinas \
  -H "Content-Type: application/json" \
  -d "{\"nome\":\"Programacao Web\",\"cargaHoraria\":80,\"matrizId\":1}"
```

Listar com paginacao:

```bash
curl "http://localhost:8081/escolas?page=0&size=10&sort=id,asc"
```

## Verificacao rapida

1. Configure o `.env`.
2. Rode `mvn clean spring-boot:run`.
3. Abra o Swagger em `http://localhost:8081/swagger-ui.html`.
4. Crie uma escola em `POST /escolas`.
5. Consulte no Neon:

```sql
SELECT * FROM escolas;
```

## Observacoes

- Nao versione credenciais reais.
- `ddl-auto=update` e pratico para desenvolvimento, mas migrations como Flyway ou
  Liquibase sao recomendadas para producao.
