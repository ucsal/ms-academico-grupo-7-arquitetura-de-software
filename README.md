# ms-academico

Microservico academico criado com Java 17, Spring Boot e Maven.

## Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Lombok
- Validation

## Configuracao

A aplicacao sobe na porta `8081` e usa o nome `ms-academico`.

Configure a conexao com o PostgreSQL Neon usando variaveis de ambiente:

```bash
DATABASE_URL=jdbc:postgresql://<host-neon>/<database>?sslmode=require
DATABASE_USERNAME=<usuario>
DATABASE_PASSWORD=<senha>
```

## Executando

```bash
mvn spring-boot:run
```
