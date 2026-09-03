Requisitos
Java 21


**comandos docker**

```bash
# sobe apenas o banco
$ docker compose up -d

# sobe banco + api
$ mvn clean package -DskipTests
$ docker compose --profile all up -d --build


# para de rodar api + bd (mantém o volume)
$ docker compose --profile all down

# para de rodar api + bd (apaga o volume)
docker compose --profile all down -v

# Recria a imagem do zero
$ docker compose --profile all up -d --build --force-recreate
```

**sem docker**

```bash
# roda o spring
$ mvn spring-boot:run
```

(Swagger)URL: http://localhost:8080/swagger-ui/index.html

## Dependencias adicionadas

* Spring Web: Criação da API REST e exposição dos endpoints.
* Spring Data JPA: Mapeamento objeto-relacional (ORM) das classes baseadas nos diagramas de projeto.
* PostgreSQL Driver: Comunicação direta com o banco de dados.
* Lombok: Redução de código boilerplate (Getters, Setters e Construtores).
* SpringDoc OpenAPI (Starter WebMVC UI): Geração e renderização automática do Swagger.
* Spring Boot DevTools: Live reload para recarregamento rápido durante o desenvolvimento.

US = User Story        → item do backlog
UC = Use Case          → Caso de Uso
SD = Sequence Diagram  → Diagrama de Sequência
