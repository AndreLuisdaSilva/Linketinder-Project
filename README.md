# Linketinder – Backend API (Groovy)

## Descrição

O **Linketinder** é uma plataforma de recrutamento inovadora que conecta candidatos e empresas de forma inteligente.  
Este projeto corresponde ao **Backend (API RESTful)** da aplicação, desenvolvido em **Groovy**, responsável pelo gerenciamento de dados, regras de negócio e persistência.

O diferencial da plataforma é o sistema de **Match Mútuo** (inspirado no Tinder) e o cálculo automático do **Índice de Afinidade**, baseado nas competências (*skills*) dos candidatos e requisitos das vagas.

---

## Tecnologias Utilizadas

- **Linguagem:** Groovy  
- **Servidor Web:** Eclipse Jetty (Embedded)  
- **Gerenciador de Dependências:** Gradle  
- **Banco de Dados:** PostgreSQL  
- **Arquitetura:** MVC com DAO (Data Access Object)  
- **Infraestrutura:** Docker  

---

## Funcionalidades

### Funcionalidades Principais

- **CRUD Completo**
  - Candidatos
  - Empresas
  - Vagas
  - Competências (*Skills*)
- **Gestão de Skills**
  - Relacionamento N:N entre Candidatos ↔ Skills
  - Relacionamento N:N entre Vagas ↔ Skills
- **API REST**
  - Endpoints padronizados
  - Respostas em JSON

### Inteligência de Negócio (Features)

#### Índice de Afinidade (Algoritmo de Recomendação)

- Ao listar vagas, o sistema calcula uma **porcentagem de compatibilidade (%)** para um candidato.
- O cálculo é baseado na interseção entre:
  - Skills do candidato
  - Skills exigidas pela vaga
- Normalização de dados:
  - Ignora diferenças entre maiúsculas e minúsculas para maior precisão.

#### Sistema de Match Mútuo

- O **match** ocorre apenas quando há interesse recíproco:
  - O candidato curte a vaga **e**
  - A empresa curte o candidato
- Suporte a **likes direcionados**:
  - A empresa pode curtir um candidato especificamente para uma vaga.

---

## Como Executar o Projeto

### Pré-requisitos

- Java JDK 11 ou superior  
- Gradle (ou Gradle Wrapper)  
- PostgreSQL (local ou via Docker)  

---

### Passo 1: Configurar o Banco de Dados (Docker)

```bash
docker build -t linketinder-db .
docker run --name linketinder-container -p 5432:5432 -d linketinder-db
```

---

### Passo 2: Iniciar a Aplicação

```bash
gradle run
```

Servidor disponível em: `http://localhost:8080`

---

## Autor

Desenvolvido por **André Luis da Silva**
