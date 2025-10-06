# Linketinder (Groovy)

## Descrição

Este é um MVP (Minimum Viable Product) de uma aplicação de networking profissional, semelhante ao Tinder, mas para fins de recrutamento. O projeto foi desenvolvido em Groovy e permite o cadastro e a listagem de candidatos e empresas.

---

## Como Executar o Projeto

Existem duas maneiras principais de executar esta aplicação:

1.  **Através de uma IDE (Ambiente de Desenvolvimento Integrado):**
    *   Importe o projeto para a sua IDE de preferência (IntelliJ, VS Code, etc.).
    *   Localize o arquivo `App.groovy` no caminho `src/main/java/com/example/demo/exerciciosgroovy/Linketinder/`.
    *   Execute o método `main` contido neste arquivo.

2.  **Via Terminal (usando o grandle Wrapper):**
    *   Abra um terminal na pasta raiz do projeto.
    *   Execute o seguinte comando:
        ```bash
                gradle run
        ```
    *   O menu da aplicação aparecerá no seu terminal.

---

## Banco de Dados

O esquema do banco de dados está definido no arquivo `sql/linketinder.sql`. Este arquivo contém todas as instruções SQL necessárias para criar as tabelas e populá-las com dados iniciais.

Quando o contêiner Docker é iniciado, este arquivo SQL é executado automaticamente para configurar o banco de dados.

### Modelo de Dados

O arquivo `sql/Diagrama sem nome.drawio` contém o diagrama entidade-relacionamento (DER) do banco de dados. Ele pode ser aberto com o [draw.io](https://app.diagrams.net/) para visualizar a estrutura e os relacionamentos entre as tabelas.

## Docker

Este projeto utiliza o Docker para criar um ambiente conteinerizado para o banco de dados PostgreSQL. O `Dockerfile` no diretório raiz do projeto é usado para construir a imagem Docker.

### Construindo a Imagem

Para construir a imagem Docker, execute o seguinte comando a partir da raiz do projeto:

```sh
docker build -t linketinder-db .
```

### Executando o Contêiner

Para executar o contêiner Docker, use o seguinte comando:

```sh
docker run --name linketinder-container -p 5432:5432 -d linketinder-db
```

Isso iniciará um contêiner de banco de dados PostgreSQL e o script `linketinder.sql` será executado para inicializar o banco de dados.

---

## Requisitos Obrigatórios

### Candidatos
- [X] Manter um array com no mínimo 5 candidatos pré-cadastrados.
- [X] Para cada candidato, salvar:
  - Nome
  - E-mail
  - CPF
  - Idade
  - Estado
  - CEP
  - Descrição Pessoal
- [X] Cada candidato deve ter um array de competências (ex: Python, Java, Spring Framework).

### Empresas
- [X] Manter um array com no mínimo 5 empresas pré-cadastradas.
- [X] Para cada empresa, salvar:
  - Nome
  - E-mail Corporativo
  - CNPJ
  - País
  - Estado
  - CEP
  - Descrição da Empresa
- [X] Cada empresa deve ter um array de competências que ela busca nos candidatos.

### Funcionalidades
- [X] Implementar um menu simples no terminal.
- [X] O menu deve ter a opção de listar todos os candidatos e todas as empresas.

---

## Requisitos Opcionais
- [X] Implementar a funcionalidade de cadastro de novos candidatos ou empresas.

---

## Dicas e Recomendações do Projeto

*   **Estrutura de Classes:** Foi criada uma interface `Ipessoa` e uma classe `Pessoa` abstrata para compartilhar atributos comuns entre `Candidato` (Pessoa Física) and `Empresa` (Pessoa Jurídica), que estendem a classe `Pessoa`.
*   **Foco no MVP:** A aplicação se concentra nos requisitos mínimos para criar um produto viável, deixando funcionalidades mais complexas para iterações futuras.
