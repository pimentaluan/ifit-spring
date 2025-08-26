
# Projeto Spring Boot - Cadastro e Listagem de Itens

## 📌 Descrição

Este projeto foi desenvolvido com **Spring Boot** e utiliza **JPA** para persistência em banco de dados.
O sistema expõe uma API REST capaz de realizar operações de **cadastro, remoção e listagem de itens**.

O projeto está dividido em dois módulos principais:

1. **Cadastro e Remoção de Itens** – permite adicionar novos itens e remover existentes.
2. **Listagem de Itens** – permite consultar todos os itens cadastrados ou buscar um item específico por ID.

---

## 🚀 Funcionalidades

* **GET /itens** → retorna todos os itens cadastrados.
* **GET /itens/{id}** → retorna um item específico pelo seu ID.
* **POST /itens** → cadastra um novo item.
* **DELETE /itens/{id}** → remove um item pelo ID.

---

## 🛠️ Tecnologias Utilizadas

* Java
* Spring Boot
* Spring Data JPA
* Banco de Dados Relacional (configurado no `application.properties`)

---

## ▶️ Como Executar

1. Clone este repositório.
2. Configure o banco de dados em `application.properties`.
3. Execute o projeto:

```bash
mvn spring-boot:run
```

4. Acesse os endpoints via navegador ou ferramentas como **Postman** ou **Insomnia**.

---

## 📂 Estrutura Simplificada

```
src/
 ├── main/
 │   ├── java/...  # Código fonte (controllers, services, repositories, models)
 │   └── resources/
 │       ├── application.properties  # Configurações do projeto
 └── test/          # Testes (opcional)
```

---
