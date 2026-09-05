# Yggdrasil

Sistema ERP desenvolvido em Java com Spring Boot, voltado para gerenciamento de produtos, estoque, vendas e usuários.

O projeto foi desenvolvido com foco em uma arquitetura organizada em camadas e na construção de uma API REST.

## Funcionalidades

* Cadastro e gerenciamento de produtos
* Controle de estoque
* Registro de vendas
* Gerenciamento de usuários
* Controle de acesso
* Dashboard de vendas e estoque
* Produtos mais vendidos
* Produtos mais lucrativos
* Desempenho de vendedores
* Consulta de faturamento por período
* Tratamento global de exceções

## Tecnologias

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* Spring Security
* MySQL
* Maven
* Git / GitHub

## Arquitetura

O projeto segue uma arquitetura em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

Os dados de entrada e saída da API são tratados através de DTOs, mantendo as entidades separadas da camada de apresentação.

## Principais endpoints

### Produtos

```text
POST   /produtos
GET    /produtos
PUT    /produtos/{id}
DELETE /produtos/{id}
```

### Usuários

```text
POST   /usuarios
GET    /usuarios
PUT    /usuarios/{id}
```

### Vendas

```text
POST /vendas
GET  /vendas/{id}
```

### Dashboards

```text
GET /dashboards/produtos-mais-vendidos
GET /dashboards/produtos-mais-lucrativos
GET /dashboards/estoque-baixo
GET /dashboards/vendedores
```

## Como executar

### Pré-requisitos

* Java 17+
* Maven
* MySQL

Clone o repositório:

```bash
git clone https://github.com/SEU_USUARIO/yggdrasil.git
cd yggdrasil
```

Configure as credenciais do banco de dados no `application.properties` e execute a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## Status

🚧 Em desenvolvimento.

O projeto está sendo desenvolvido e novas funcionalidades serão adicionadas futuramente, incluindo a interface gráfica da aplicação web e desktop.
<p align="center">
  <img src="https://i.pinimg.com/originals/72/0c/c4/720cc43d757ee638ad5054a05220fafe.gif" alt="Demonstração do Yggdrasil">
</p>
