# Sistema de Vendas JDBC

Projeto de estudo desenvolvido em Java e PostgreSQL com o objetivo de praticar conceitos de Programação Orientada a Objetos, acesso a banco de dados e desenvolvimento Back-end.

## Tecnologias utilizadas

- Java
- PostgreSQL
- JDBC
- Maven

## Conceitos aplicados

- Operações CRUD (Create, Read, Update e Delete).
- Conexão e manipulação de dados utilizando JDBC.
- PreparedStatement e ResultSet.
- Padrão DAO (Data Access Object).
- Separação de responsabilidades entre Model, DAO e Service.
- Injeção de dependência por construtor.
- Validação de dados e tratamento de exceções.
- Transações com commit e rollback.
- Relacionamentos entre tabelas no PostgreSQL.

## Estrutura do projeto

```text
br.com.sistemavendas/
│
├── dao/
│   ├── ClienteDAO.java
│   ├── ItemPedidoDAO.java
│   ├── PedidoDAO.java
│   └── ProdutoDAO.java
│
├── factory/
│   └── ConnectionFactory.java
│
├── model/
│   ├── Cliente.java
│   ├── ItemPedido.java
│   ├── Pedido.java
│   └── Produto.java
│
├── service/
│   ├── ClienteService.java
│   ├── ItemPedidoService.java
│   ├── PedidoService.java
│   └── ProdutoService.java
│
└── Main.java
```

## Objetivo

Consolidar os fundamentos de Java e JDBC por meio de um projeto prático, desenvolvendo conhecimentos sobre persistência de dados, organização em camadas e regras de negócio.

Este projeto faz parte da minha jornada de aprendizado em desenvolvimento Back-end Java, servindo como preparação para os estudos de Spring Boot.

Este projeto não tem como objetivo desenvolver um sistema completo de gerenciamento de uma pizzaria ou uma solução comercial pronta para produção.

Trata-se de um projeto de estudo, desenvolvido para praticar e consolidar conceitos de Java, Programação Orientada a Objetos, JDBC e integração com PostgreSQL.