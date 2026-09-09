# Requisitos

- Java 21
- Maven
- PostgreSQL
- Docker e Docker Compose (opcional)

## Como rodar

Execute os comandos abaixo na pasta `hackathon-api`.

Antes de iniciar o Spring, o PostgreSQL precisa estar rodando e o banco `hackathondb` deve existir. O Flyway cria as tabelas e insere os dados de exemplo dentro desse banco; ele não cria o banco em si.

**comandos docker**

O Docker Compose está configurado com `POSTGRES_DB: hackathondb`, que cria o banco automaticamente na primeira inicialização de um volume vazio. Se você já tem um volume e o banco não existe, suba o serviço e crie o banco manualmente, sem apagar os dados existentes:

```bash
docker compose up -d db
docker compose exec db psql -U admin -d postgres -c "CREATE DATABASE hackathondb;"
```

O comando de criação só precisa ser executado se o banco ainda não existir.

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

```sql
CREATE DATABASE hackathondb;
```

### Configuração

A aplicação utiliza variáveis de ambiente para configurações.

Confira a conexão no `application.yaml`: por padrão, a aplicação usa `localhost:5432`, banco `hackathondb`, usuário `postgres` e senha `1234`. Esse usuário precisa existir e ter permissão para criar tabelas no banco (TODO adicionar env). Se usar outro usuário, ajuste as credenciais ou configure `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`.

Para execução local, configure:

```bash
export JWT_SECRET=<sua-chave-jwt>
export ADMIN_EMAIL=<email-do-administrador>
export ADMIN_PASSWORD=<senha-do-administrador>
```

A JWT_secret pode ser gerada com:
```bash
openssl rand -base64 32
```

Depois de criar o banco, inicie a aplicação. O Flyway executará automaticamente as migrations pendentes.

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
* flyway: controla as alterações do banco por arquivos SQL versionados.

US = User Story        → item do backlog
UC = Use Case          → Caso de Uso
SD = Sequence Diagram  → Diagrama de Sequência
