````markdown
# Comparador de Planilhas

Projeto desenvolvido em **Java com Spring Boot** para gerenciamento de usuários, importação de arquivos CSV, cadastro de planilhas e comparação de planilhas utilizando **MongoDB**.

## Objetivo

O sistema foi criado para simular um fluxo completo entre cadastro de usuários e operações com planilhas, permitindo:

- cadastro manual de usuários
- importação de usuários por arquivo CSV
- cadastro de planilhas
- comparação entre planilhas cadastradas
- navegação por interface web com **Thymeleaf**
- testes de endpoints por **Swagger** e **Postman**

---

## Estrutura do projeto

```text
comparador-planilha/
├── api-usuarios/
└── api-planilhas/
````

### API Usuários

Responsável pelo cadastro e consulta de usuários no MongoDB.

### API Planilhas

Responsável pelo cadastro de planilhas, comparação entre planilhas e importação de usuários por CSV.

---

## Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data MongoDB
* Thymeleaf
* MongoDB
* Swagger / OpenAPI
* Maven
* Lombok

---

## Funcionalidades

### API de Usuários

* cadastrar usuário
* listar usuários
* buscar usuário por id

### API de Planilhas

* cadastrar planilha
* listar planilhas
* buscar planilha por id
* cadastrar comparação entre planilhas
* listar comparações
* buscar comparação por id
* importar usuários via CSV

### Interface Web

* tela de importação de CSV
* tela de cadastro de usuário
* tela de compra/cadastro de planilha
* navegação entre páginas com Thymeleaf

---

## Endpoints principais

### Usuários

* `GET /usuarios`
* `POST /usuarios`
* `GET /usuarios/{id}`

Swagger da API de usuários:

```text
http://localhost:8080/swagger-ui/index.html
```

### Planilhas

* `GET /planilhas`
* `POST /planilhas`
* `GET /planilhas/{id}`

### Comparações

* `GET /comparacoes`
* `POST /comparacoes`
* `GET /comparacoes/{id}`

### Importação CSV

* `GET /importdata`
* `POST /importdata`

Swagger da API de planilhas:

```text
http://localhost:8081/swagger-ui/index.html
```

---

## Rotas web com Thymeleaf

Exemplos de páginas disponíveis na API de planilhas:

* `/upload`
* `/usuarios/novo`
* `/planilhas/comprar`

---

## Banco de dados

O projeto utiliza **MongoDB**.

### Collections principais

* `usuarios`
* `planilhas`
* `comparacoes`
* `import_users`

---

## Exemplo de CSV para importação

```csv
nome,telefone,logradouro,numero,cep,bairro,cidade
Lucas Cabrio,16999990001,Rua das Flores,123,14800000,Centro,Araraquara
Maria Silva,16999990002,Avenida Brasil,456,14801010,Vila Xavier,São Carlos
João Pereira,16999990003,Rua São João,78,14802020,Jardim Paulista,Matão
Ana Souza,16999990004,Rua XV de Novembro,910,14803030,Santa Angelina,Américo Brasiliense
Carlos Mendes,16999990005,Avenida Paulista,55,14804040,São José,Ribeirão Preto
```

---

## Como executar o projeto

### Pré-requisitos

* Java 21
* MongoDB em execução
* Maven instalado ou Maven Wrapper

### Executando a API de usuários

Entre na pasta da API e execute:

```bash
mvn spring-boot:run
```

ou

```bash
mvnw.cmd spring-boot:run
```

### Executando a API de planilhas

Entre na pasta da API e execute:

```bash
mvn spring-boot:run
```

ou

```bash
mvnw.cmd spring-boot:run
```

---

## Como testar

### 1. API Usuários

Utilize o Swagger da API de usuários para:

* cadastrar usuários manualmente
* listar usuários
* consultar usuários por id

### 2. API Planilhas

Utilize o Swagger da API de planilhas para:

* cadastrar planilhas
* listar planilhas
* cadastrar comparações
* consultar comparações

### 3. Importação CSV

Você pode testar a importação:

* via **Postman**
* via tela web em `/upload`

Para importar pelo Postman:

* método `POST`
* URL `http://localhost:8081/importdata`
* Body `form-data`
* campo `file`
* tipo `File`
* anexar um arquivo `.csv`

---

## Observações

* a API de planilhas depende da API de usuários para algumas validações
* a importação CSV foi implementada para arquivos simples separados por vírgula
* a interface web foi construída com Thymeleaf para facilitar a visualização e interação com o sistema
* o projeto foi desenvolvido com foco em estudo prático de Spring Boot, MongoDB, upload de arquivos e integração entre APIs

---

## Autor

**Lucas Cabrio**

```
```
