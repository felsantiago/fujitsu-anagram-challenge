## 💡 Pergunta 2

**Provide an example scenario where overriding the equals() method is necessary in Java. Explain the key considerations when implementing this method, such as ensuring it aligns with the hashCode() method. Include code examples if possible.**

---

#### ✅ Resposta

No Java, a comparação padrão de objetos é feita por **referência de memória**, não pelo valor interno.
Ou seja, dois objetos que "parecem iguais" podem ser considerados diferentes se estiverem em endereços diferentes.

Por isso, **sempre que quisermos comparar objetos baseados nos seus dados** (e não na posição na memória), precisamos sobrescrever o método `equals()` — principalmente em coleções como `Set`, `Map`, ou em operações como `contains()

---

## 🧾 Exemplo prático: classe `Customer`

Vamos supor que no nosso sistema o que identifica um cliente seja o seu `document` (CPF/CNPJ). Faz sentido comparar clientes por esse atributo.

```java
import java.util.Objects;

public class Customer {

  private String document;

  public Customer(String document) {
    this.document = document;
  }

  public String getDocument() {
    return document;
  }

  public void setDocument(final String document) {
    this.document = document;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof final Customer customer)) {
      return false;
    }
    return Objects.equals(document, customer.document);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(document);
  }
}
```

Assim, dois clientes com o **mesmo documento** são tratados como o mesmo cliente.

---

## 🔍 Comportamento real sem e com sobrescrita

### ❌ Sem sobrescrever `equals()`

```java
Customer c1 = new Customer("123");
Customer c2 = new Customer("123");

System.out.println(c1.equals(c2)); // false
```

Mesmo que os dados sejam iguais, são dois objetos diferentes na memória — e o Java entende que são distintos.

---

### ✅ Com `equals()` implementado

```java
Customer c1 = new Customer("123");
Customer c2 = new Customer("123");

System.out.println(c1.equals(c2)); // true
```

Agora sim: o sistema compara o conteúdo (`document`) e reconhece que são iguais.

---

## 🧠 E se eu apenas copiar a referência?

Se você fizer:

```java
Customer c1 = new Customer("123");
Customer c2 = c1;
```

Você está apenas copiando o **endereço de memória**. Ou seja, `c1` e `c2` apontam para exatamente o mesmo objeto, então:

```java
System.out.println(c1.equals(c2)); // true
```

(com ou sem sobrescrever `equals`).

---

## 🔄 E se eu reatribuir `c1`?

```java
Customer c1 = new Customer("123");
Customer c2 = c1; // mesma referência

c1 = new Customer("456");

System.out.println(c1.equals(c2)); // false
```

Depois do `new Customer("456")`, `c1` aponta para um novo objeto, mas `c2` ainda aponta para o anterior.

---

## 📦 Exemplo clássico com `Set`

```java
Set<Customer> customers = new HashSet<>();
customers.add(new Customer("123"));
customers.add(new Customer("123"));

System.out.println(customers.size()); // 1 (se equals/hashCode foram sobrescritos)
```

Sem `equals()` e `hashCode()` sobrescritos, o Java consideraria dois objetos diferentes e o tamanho seria `2`.

---

## ✅ Dicas para escrever `equals()` corretamente

- **Consistência** entre `equals()` e `hashCode()`: se dois objetos são iguais (`equals`), eles **devem** ter o mesmo `hashCode`.
- **Reflexividade**: `a.equals(a)` sempre deve ser `true`.
- **Simetria**: `a.equals(b)` implica que `b.equals(a)`.
- **Transitividade**: se `a.equals(b)` e `b.equals(c)`, então `a.equals(c)`.
- **Consistência**: o resultado de `equals` não pode mudar se o objeto não mudar.
- **Não pode quebrar com `null`**: `a.equals(null)` deve sempre retornar `false`.

---

## 🧠 Em resumo:

Quando comparamos objetos como `Customer`, `Product`, ou `Employee`, quase sempre queremos comparar o **valor dos atributos**, e não o endereço na memória.

Por isso, **sempre** que tiver uma identidade lógica no seu objeto (como ID, CPF, documento, etc.), **sobrescreva** `equals()` e `hashCode()`.