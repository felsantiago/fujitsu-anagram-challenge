## 💡 Pergunta 4

**Discuss the techniques you use to prevent SQL injection attacks in web applications. Provide examples of code showing secure implementations, such as using parameterized queries or ORMs. Mention any additional measures you take to secure the database layer.**

#### ✅ Resposta

Para prevenir SQL Injection, sigo um conjunto de boas práticas que envolvem o uso de **consultas parametrizadas**, **ORMs seguros**, e medidas adicionais de proteção na camada de acesso ao banco.

---

### 🧱 1. Uso de ORM ou consultas parametrizadas

O uso de frameworks como **JPA/Hibernate** garante que os parâmetros sejam tratados como dados e não como código executável.

```java
@Query("SELECT u FROM User u WHERE u.email = :email")
User findByEmail(@Param("email") String email);
```

No caso de uso direto de JDBC, sempre utilizo `PreparedStatement`:

```java
String sql = "SELECT * FROM users WHERE email = ?";
PreparedStatement stmt = connection.prepareStatement(sql);
stmt.setString(1, email);
```

---

### 🛡️ 2. Medidas complementares de segurança

- **Validação de entrada**: dados são validados e normalizados antes de qualquer operação.
- **Princípio do menor privilégio**: usuários de banco têm apenas as permissões necessárias (ex: leitura, escrita).
- **Evito SQL dinâmico baseado em input**: lógica condicional é sempre tratada de forma controlada.
- **Logging seguro**: nunca logar queries com dados sensíveis ou input do usuário diretamente.

---

### 🧠 Conclusão

Mesmo com uso de ORM, sempre assumo que dados externos podem ser maliciosos. Combinando **queries seguras**, **validação de entrada** e **gestão de privilégios**, é possível mitigar completamente o risco de SQL Injection em aplicações web.
