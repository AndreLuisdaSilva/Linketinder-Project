
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

2.  **Via Terminal (usando o Maven Wrapper):**
    *   Abra um terminal na pasta raiz do projeto.
    *   Execute o seguinte comando:
        ```bash
        mvn spring-boot:run ou groovy Main.groovy
        ```
    *   O menu da aplicação aparecerá no seu terminal.

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