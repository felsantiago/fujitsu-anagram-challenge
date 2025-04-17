## 💡 Pergunta 8

**Consider the following description of a system functionality: `User Registration`

- A screen allows users to insert, delete, or update user information.
- Each user has properties: name, email, address, and phone, where name and email are mandatory fields.
- Emails must be unique across all users.
- Only admin users can delete other users.

Task:

1. Describe the types of tests you would implement (e.g., unit, integration, or end-to-end tests) and explain the scenarios you would test to ensure the functionality works as expected.
2. Provide examples of edge cases and how you would handle them.
3. Include an example of a test case in code or pseudocode for one or more scenarios.**

#### ✅ Resposta

#### 🎯 Descrição da Funcionalidade

Tela de cadastro de usuários permite inserir, atualizar e deletar registros.
Campos do usuário: `nome`, `email`, `endereço`, `telefone`.
- `nome` e `email` são obrigatórios
- `email` deve ser único
- Somente usuários com perfil `admin` podem excluir outros usuários

---

#### 🧪 Tipos de Testes a Implementar

| Tipo de Teste        | Objetivo                                                                 |
|----------------------|--------------------------------------------------------------------------|
| **Teste Unitário**   | Validar regras isoladas (ex: email único, campos obrigatórios)           |
| **Teste de Integração** | Verificar a interação entre camadas (controller → service → repository) |
| **Teste End-to-End (E2E)** | Simular o fluxo completo via interface ou API                        |

---

#### ✅ Casos Positivos

1. Criar usuário com nome e email válidos → sucesso
2. Atualizar endereço e telefone → sucesso
3. Deletar usuário como `admin` → sucesso
4. Buscar lista de usuários → dados exibidos corretamente

---

#### ❌ Casos Negativos e de Borda

| Cenário                                                    | Resultado Esperado                                      |
|-------------------------------------------------------------|----------------------------------------------------------|
| Criar usuário com email já existente                        | Erro: "Email já em uso"                                  |
| Criar usuário com nome ou email em branco                   | Erro de validação ("Nome e email são obrigatórios")     |
| Criar usuário com email inválido (ex: `abc@`)               | Erro: "Email inválido"                                   |
| Deletar usuário como não-admin                              | Ação bloqueada / Erro de permissão                       |
| Atualizar email para outro já cadastrado                    | Erro de conflito                                         |
| Inserir telefone com caracteres não numéricos               | Erro de validação                                        |
| Endereço com caracteres especiais ou tamanho excessivo      | Validação e sanitização de entrada                       |

---

#### 🧪 Exemplos de Testes

#### 📦 Teste Unitário – Validação de Email Único

```java
@Test
void shouldThrowExceptionWhenEmailAlreadyExists() {
    var existingUser = new User("João", "joao@email.com");
    userRepository.save(existingUser);

    var newUser = new User("Carlos", "joao@email.com");

    assertThrows(EmailAlreadyExistsException.class, () -> {
        userService.createUser(newUser);
    });
}
```

---

#### 🔄 Teste de Integração – Deleção com Permissão

#### ✅ Usando JUnit

```java
@Test
void adminShouldDeleteUserSuccessfully() {
    var admin = authenticateAs("admin");
    var user = userService.createUser("Maria", "maria@email.com");

    userService.deleteUser(admin, user.getId());

    assertFalse(userRepository.existsById(user.getId()));
}
```

### **ou**

#### ✅ Usando Cucumber + Spring (Gherkin)

```gherkin
Feature: Exclusão de Usuário

  Scenario: Deletar usuário como administrador
    Given estou autenticado como administrador
    And um usuário com email "maria@email.com" existe
    When eu envio a requisição de deleção para esse usuário
    Then o sistema deve remover o usuário com sucesso
```

```java
// Step Definition (Java + Spring)
@Given("estou autenticado como administrador")
public void autenticarComoAdmin() {
    authContext.setCurrentUser("admin");
}

@And("um usuário com email {string} existe")
public void criarUsuario(String email) {
    userService.createUser("Maria", email);
}

@When("eu envio a requisição de deleção para esse usuário")
public void deletarUsuario() {
    userService.deleteUser(authContext.getCurrentUser(), "maria@email.com");
}

@Then("o sistema deve remover o usuário com sucesso")
public void verificarRemocao() {
    assertFalse(userRepository.existsByEmail("maria@email.com"));
}
```

---

#### 🌐 Teste E2E (BDD) – Cadastro via Interface

```gherkin
Feature: Cadastro de Usuário

  Scenario: Criar novo usuário com dados válidos
    Given estou na tela de cadastro
    When preencho "Nome" com "Felipe" e "Email" com "felipe@email.com"
    And clico em "Salvar"
    Then devo ver a mensagem "Usuário cadastrado com sucesso"
```

---

#### ✅ Conclusão

- Testes unitários garantem regras de negócio isoladas
- Testes de integração cobrem fluxo entre camadas e segurança
- E2E simula comportamento real do usuário
- Casos de borda aumentam a confiabilidade
- Estratégia de testes ajuda a garantir qualidade e regressão segura em CI/CD