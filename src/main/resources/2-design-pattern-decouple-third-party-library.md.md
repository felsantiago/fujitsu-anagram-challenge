## 💡 Pergunta 2

**Explain how you would use a design pattern to decouple your code from a third-party library that might be replaced in the future. Describe the advantages and limitations of your chosen approach, and provide a small code snippet illustrating its application.**

---

#### ✅ Resposta

Para desacoplar a aplicação de uma biblioteca de mensageria (como o Kafka), eu aplicaria o **Padrão Adapter** em conjunto com os princípios **SOLID**.

O objetivo é garantir que a aplicação dependa apenas de uma interface estável, e não diretamente do SDK externo. Isso permite que, no futuro, o Kafka seja substituído por outro sistema de mensageria (como SNS + SQS) com o mínimo de impacto.

---

#### 1. ISP – Princípio da Segregação de Interface

Definimos uma interface para abstrair o contrato de mensageria:

```java
public interface MessagingService {
    void sendMessage(String topic, String message);
    String receiveMessage(String topic);
}
```

Essa abstração evita que a aplicação dependa diretamente de APIs específicas como a do Kafka.

---

#### 2. Adapter Pattern + SRP – Adapter para Kafka

Implementamos a interface especificamente para o Kafka:

```java
public class KafkaAdapter implements MessagingService {
    private final KafkaProducer<String, String> producer;

    public KafkaAdapter(KafkaProducer<String, String> producer) {
        this.producer = producer;
    }

    @Override
    public void sendMessage(String topic, String message) {
        producer.send(new ProducerRecord<>(topic, message));
    }

    @Override
    public String receiveMessage(String topic) {
        // Exemplo simplificado de consumo Kafka
        return "mensagem recebida via Kafka";
    }
}
```

---

#### 3. Adapter Pattern + SRP – Adapter para SNS + SQS

Criamos uma segunda implementação usando AWS SNS e SQS:

```java
public class SnsSqsAdapter implements MessagingService {
    private final AmazonSNS snsClient;
    private final AmazonSQS sqsClient;

    public SnsSqsAdapter(AmazonSNS snsClient, AmazonSQS sqsClient) {
        this.snsClient = snsClient;
        this.sqsClient = sqsClient;
    }

    @Override
    public void sendMessage(String topicArn, String message) {
        snsClient.publish(topicArn, message);
    }

    @Override
    public String receiveMessage(String queueUrl) {
        return sqsClient.receiveMessage(queueUrl).getMessages().get(0).getBody();
    }
}
```

---

#### 4. DIP – Princípio da Inversão de Dependência

A aplicação depende da abstração `MessagingService`, e não de uma implementação concreta:

```java
public class OrderService {
    private final MessagingService messagingService;

    public OrderService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void confirmOrder(String payload) {
        messagingService.sendMessage("order.confirmed", payload);
    }
}
```

---

#### 5. OCP + LSP – Princípios do Aberto/Fechado e da Substituição de Liskov

Em tempo de execução, injetamos o adapter desejado sem modificar a lógica da aplicação:

```java
// MessagingService service = new KafkaAdapter(kafkaProducer);
// ou
MessagingService service = new SnsSqsAdapter(snsClient, sqsClient);

OrderService orderService = new OrderService(service);
orderService.confirmOrder("{"id": 123}");
```

---

#### ✅ Vantagens

- **Desacoplamento**: a lógica da aplicação fica isolada da API externa.
- **Testabilidade**: fácil de mockar a interface em testes unitários.
- **Extensível**: permite adicionar novos provedores sem alterar a regra de negócio.
- **Independência de fornecedor**: facilita a troca de tecnologia.

---

#### ⚠️ Limitações

- Introduz uma camada a mais de abstração.
- Exige manutenção de múltiplas implementações da interface.

---

### 🧠 Conclusão

Combinando o **Padrão Adapter** com os princípios do **SOLID** (especialmente ISP, DIP, OCP e SRP), obtemos uma arquitetura limpa, desacoplada e preparada para mudanças de fornecedor sem impactar a lógica da aplicação.
