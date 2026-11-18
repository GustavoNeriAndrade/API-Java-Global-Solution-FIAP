# Desperdício Zero – API (Global Solution 2026/2 · CitizenTech)

API REST em Java (Spring Boot) do Desperdício Zero: casas, comunidades e pequenos negócios registram o
desperdício de água, energia e alimentos, definem metas mensais e recebem dicas de economia.

- **Front-end:** repositório do front (React + Vite + TypeScript)
- **API publicada (Render):** _(preencher a URL)_
- **Vídeo de demonstração:** _(preencher o link do YouTube não listado)_

## Integrantes

| Nome completo                | RM         | Turma      |
|------------------------------|------------|------------|
| _(Gustavo Neri Andrade)_     | _(572722)_ | _(1TDSPI)_ |
| _(Miguel Vieira Martins)_    | _(571978)_ | _(1TDSPI)_ |
| _(Carlos Americo Machado)_   | _(571250)_ | _(1TDSPI)_ |
| _(Thiado Vendrami Luca)_     | _(572942)_ | _(1TDSPI)_ |
| _(Murilo da Silva Lourenço)_ | _(573959)_  | _(1TDSPI)_ |

## Arquitetura

`controller` (rotas REST) → `service` (regras de negócio) → `dao` (JDBC puro, `DataSource` no construtor e
`PreparedStatement`) → Oracle. Os DTOs (`dto`) são convertidos com ModelMapper, os erros saem em JSON padronizado
pelo `handler` e o CORS fica em `config`.

Não há JPA, Hibernate, Spring Data nem JdbcTemplate. O `spring-boot-starter-jdbc` é usado só para criar o
`DataSource` (HikariCP).

## Como rodar localmente

1. No Oracle da FIAP, rode `sql/gs_ddl_desperdicio_zero.sql` (tabelas e sequences) e depois os INSERTs do script completo.
2. Defina as variáveis de ambiente (nunca coloque usuário e senha no código):

| Variável | Para quê |
| --- | --- |
| `DB_USER` | usuário do Oracle (RM) |
| `DB_PASSWORD` | senha do Oracle |
| `DB_URL` | opcional; padrão `jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl` |
| `APP_CORS_ORIGENS` | domínios do front separados por vírgula, sem `/` no final. Ex.: `http://localhost:5173,https://seu-front.vercel.app` |
| `TZ` | `America/Sao_Paulo` (o Dockerfile já define) |

3. Rode `mvn spring-boot:run` (ou execute `DesperdicioZeroApplication` no IntelliJ com as variáveis acima).
4. Abra `http://localhost:8080/` — deve responder `{"api":"Desperdicio Zero","status":"no ar"}`.

## Endpoints

| Método | Rota | Descrição | Status |
| --- | --- | --- | --- |
| GET | `/locais` | lista os locais | 200 |
| POST | `/locais` | cadastra local | 201 + `Location`, 400 |
| GET | `/locais/{id}` | busca local | 200, 404 |
| PUT | `/locais/{id}` | atualiza local | 200, 400, 404 |
| DELETE | `/locais/{id}` | exclui local (e suas metas) | 204, 404, **409** se tem registros |
| GET | `/locais/{id}/registros` | histórico do local | 200, 404 |
| POST | `/locais/{id}/registros` | lança desperdício | 201 + `Location`, 400, 404, 409 |
| GET | `/registros/{id}` | busca registro | 200, 404 |
| PUT | `/registros/{id}` | atualiza registro | 200, 400, 404, 409 |
| DELETE | `/registros/{id}` | exclui registro | 204, 404 |
| GET | `/locais/{id}/metas` | metas do local | 200, 404 |
| POST | `/locais/{id}/metas` | define meta mensal | 201 + `Location`, 400, 404, **409** meta duplicada |
| GET | `/metas/{id}` | busca meta | 200, 404 |
| PUT | `/metas/{id}` | atualiza meta | 200, 400, 404, 409 |
| DELETE | `/metas/{id}` | remove meta | 204, 404 |
| GET | `/recursos` | água, energia e alimento | 200 |
| GET | `/causas?recurso={id}` | causas (todas ou de um recurso) | 200 |
| GET | `/dicas?recurso={id}` | dicas (todas ou de um recurso) | 200 |

Métodos não suportados em uma rota devolvem **405**. Todo erro tem o formato:

```json
{ "status": 409, "message": "Este local já tem registros e não pode ser excluído." }
```

Erros de validação (400) trazem também `campos`, com a mensagem de cada campo inválido.

## Deploy no Render (Docker)

1. Suba o projeto para o GitHub (o `.gitignore` já ignora `target/`, `.env` e `.idea/`).
2. No Render: **New > Web Service**, escolha o repositório e o ambiente **Docker** (usa o `Dockerfile` da raiz).
3. Em **Environment**, cadastre `DB_USER`, `DB_PASSWORD` e `APP_CORS_ORIGENS` (com o domínio do front na Vercel).
4. Aguarde o build e teste `https://SUA-API.onrender.com/` e `https://SUA-API.onrender.com/locais`.
5. No front, configure `VITE_API_URL=https://SUA-API.onrender.com` e `VITE_API_MODE=java` (também na Vercel).

> No plano gratuito do Render a API "dorme" após um tempo sem uso; a primeira chamada pode levar cerca de um minuto.
