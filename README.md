# 📋 Task Manager API

Uma API RESTful desenvolvida com Spring Boot para gerenciamento de tarefas com autenticação JWT, controle de usuários,
paginação, filtros e documentação Swagger.

---

# 🚀 Tecnologias utilizadas

- Java 25
- Spring Boot 4
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- H2 Database
- Swagger / OpenAPI
- Maven
- Lombok

---

# 🔐 Funcionalidades

## Usuários

- Criar usuário
- Atualizar usuário autenticado
- Buscar usuário autenticado
- Deletar usuário autenticado

## Autenticação

- Login com JWT
- Proteção de rotas com Spring Security
- Autenticação Stateless

## Tasks

- Criar task
- Listar tasks do usuário autenticado
- Buscar task por ID
- Atualizar task
- Deletar task

## Recursos avançados

- Paginação
- Ordenação
- Filtros por status
- Filtros por prioridade
- Tratamento global de exceptions
- Validação de dados
- Documentação Swagger

---

# 📚 Documentação Swagger

Após iniciar a aplicação:

http://localhost:8080/swagger-ui.html

---

# ⚙️ Como executar o projeto

## Clone o repositório

```bash
git clone https://github.com/4-lan/task-manager-api.git