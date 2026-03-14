# Comparador de Planilhas

Projeto desenvolvido em **Java com Spring Boot** para realizar cadastro e comparação de planilhas utilizando **MongoDB**.

## Estrutura do projeto

comparador-planilhas
- api-usuarios
- api-planilhas

## Tecnologias

- Java 21
- Spring Boot
- MongoDB
- Swagger
- Maven

## APIs

### API Usuários
Responsável pelo cadastro de usuários.

Endpoints principais:

GET /usuarios  
POST /usuarios  
GET /usuarios/{id}

Swagger:

http://localhost:8080/swagger-ui/index.html

---

### API Planilhas
Responsável por cadastrar planilhas e realizar comparações.

Endpoints principais:

GET /planilhas  
POST /planilhas  
GET /planilhas/{id}

Comparações:

GET /comparacoes  
POST /comparacoes  
GET /comparacoes/{id}

Swagger:

http://localhost:8081/swagger-ui/index.html

## Banco de Dados

MongoDB

Collections:

usuarios  
planilhas  
comparacoes

## Autor

Lucas Cabrio
