## 💡 Pergunta 7

**The customer has a system called XYZ and intends to start updates split into 3 phases.

The requirements for the first phase are as follows:

1. Enable new data entries in the system, which will serve as input for the second phase.

2. Implement functionality to create, update, delete, and search plants.
- Plants should have the following attributes:
    - Code: Numeric only, mandatory, and unique.
    - Description: Alphanumeric, up to 10 characters, optional.
- Only admin users can delete plants.

3. Ensure that the system prevents duplication of plant codes.
Task:
Based on the above information:

    1. Write a use case or user story for this scenario, ensuring that it clearly addresses the requirements.
    2. Highlight any business rules or assumptions relevant to the solution.
    3. Describe any validations or security measures you would implement in the system.
    4. Suggest how you would test this functionality, including examples of edge cases.**

[!image](./images/tabelas.png)

#### ✅ Resposta

#### 🧾 User Story

**Como** um usuário autenticado do sistema XYZ
**Quero** cadastrar, editar, consultar e visualizar registros de plantas
**Para que** esses dados sejam utilizados nas próximas fases da plataforma.

**Como** um administrador do sistema
**Quero** ter permissão para excluir plantas cadastradas
**Para que** os dados fiquem sempre atualizados e consistentes.

---

#### 📜 Regras de Negócio

1. O código da planta:
   - Deve ser **numérico**
   - É **obrigatório**
   - Deve ser **único** (não pode haver duplicidade)

2. A descrição da planta:
   - É **opcional**
   - Deve ser **alfanumérica**
   - Pode conter até **10 caracteres**

3. Exclusão:
   - Apenas usuários com perfil **admin** podem excluir registros

---

#### 🔐 Validações e Requisitos de Segurança

- Validação de código numérico e unicidade no backend (e frontend quando possível)
- Validação de limite de caracteres na descrição
- Controle de acesso com base em papéis (RBAC)
- Logs de auditoria para ações de criação, edição e exclusão
- Proteção contra requisições forjadas (CSRF)
- Sessão ativa obrigatória e autenticação JWT para APIs

---

#### ✅ Critérios de Aceitação

- O sistema permite cadastrar plantas com código único e descrição válida
- Não é possível cadastrar duas plantas com o mesmo código
- A descrição não pode ultrapassar 10 caracteres
- A exclusão de plantas está disponível apenas para usuários administradores
- A busca funciona por código exato ou parte da descrição

---

#### 🧪 Cenários de Teste em Gherkin (BDD)

```gherkin
Feature: Cadastro de Plantas
  Como um usuário do sistema
  Quero gerenciar registros de plantas
  Para que eles possam ser utilizados em fases futuras do projeto

  Scenario: Criar planta com código e descrição válidos
    Given estou autenticado no sistema
    When eu cadastro uma planta com código "100" e descrição "Planta A"
    Then a planta deve ser criada com sucesso

  Scenario: Criar planta com código sem descrição
    Given estou autenticado no sistema
    When eu cadastro uma planta com código "200" e sem descrição
    Then a planta deve ser criada com sucesso

  Scenario: Editar descrição de uma planta existente
    Given uma planta com código "100" existe
    When eu altero a descrição para "NovaDesc"
    Then a descrição deve ser atualizada corretamente

  Scenario: Criar planta com código duplicado
    Given uma planta com código "100" já existe
    When tento cadastrar outra planta com o mesmo código
    Then o sistema deve exibir um erro de duplicidade

  Scenario: Criar planta com código inválido
    When tento cadastrar uma planta com código "ABC"
    Then o sistema deve exibir um erro de validação

  Scenario: Criar planta com descrição maior que o permitido
    When tento cadastrar uma planta com descrição "DescriçãoMuitoLonga"
    Then o sistema deve exibir um erro de validação

  Scenario: Excluir planta como administrador
    Given estou logado como administrador
    When eu excluo a planta com código "100"
    Then a planta deve ser removida com sucesso

  Scenario: Excluir planta com perfil não administrador
    Given estou logado como usuário comum
    When tento excluir uma planta
    Then o sistema deve negar a operação com mensagem de acesso negado
```

---

#### 💡 Assunções Técnicas

- O sistema implementa autenticação via OAuth2/JWT
- O backend é construído em REST (Spring Boot, Node.js, etc)
- O frontend possui validação client-side (React, Angular)
- As permissões são aplicadas via RBAC, integradas ao provedor de identidade (ex: Keycloak)

---

#### 📌 Observação

Este documento segue o padrão utilizado em grandes empresas para squads ágeis, unificando requisitos funcionais, critérios de aceite e testes automatizados em BDD, com foco em clareza e rastreabilidade técnica e funcional.
