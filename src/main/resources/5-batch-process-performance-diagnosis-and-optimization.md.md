## 💡 Pergunta 5

**Describe the steps you would take to diagnose and improve the performance of a batch process that interacts with a database and an FTP server. Explain how you would identify bottlenecks, optimize database queries, improve logic execution, and enhance file transfer efficiency. Provide examples of tools or techniques you would use during the analysis.**

#### ✅ Resposta

Para diagnosticar e melhorar a performance de um processo batch que interage com banco de dados e FTP, sigo uma abordagem estruturada, com foco em observabilidade, paralelismo e potencial de modernização.

---

#### 🔍 1. Diagnóstico de Gargalos

- Logs temporizados entre etapas (query, processamento, envio)
- Ferramentas como **VisualVM**, **Java Flight Recorder**
- Análise de queries com **EXPLAIN PLAN**, **AWR**, ou **pg_stat_statements**
- Monitoramento de FTP com **lftp -d**, **Wireshark** ou logs de sessão

---

#### 🛢️ 2. Otimização de Banco de Dados

- Aplicação de **índices** em colunas de filtro e junção
- Uso de **paginação (LIMIT/OFFSET)** ou leitura incremental
- Evitar `SELECT *`, reduzir volume de dados lidos
- Substituição de consultas `N+1` por `JOINs` ou `IN`

---

#### 🧠 3. Melhoria da Lógica de Execução

- Divisão do processo em **chunks controlados**
- **Paralelismo** com `ExecutorService`, `CompletableFuture`
- Uso de **Stream API** para processar dados em fluxo contínuo
- Escrita temporária em disco para buffers intermediários, se necessário

---

#### 📡 4. Eficiência no FTP

- Compressão de arquivos (ZIP/GZIP) antes do envio
- Transferência paralela com múltiplas threads
- Ajuste de buffers, timeouts, e controle de falhas
- Alternativa com **AWS Transfer Family** (FTP gerenciado com backend em S3)

---

#### 🚀 Visão Evolutiva – Arquitetura com AWS Firehose

Caso o cliente aceite modernizar o processo, proponho:

- Leitura contínua da base de dados (por polling incremental)
- Envio direto para o **Amazon Kinesis Firehose**
- Firehose agrega, comprime e entrega em **S3, Redshift ou Lambda**
- Se o cliente ainda exigir FTP, um **AWS Lambda** detecta arquivos no S3 e envia via FTP legado

---

#### 🛠️ Ferramentas e Técnicas

| Etapa                    | Ferramenta / Técnica                          |
|--------------------------|-----------------------------------------------|
| Diagnóstico da execução  | VisualVM, JFR, logs detalhados                |
| SQL Performance          | EXPLAIN PLAN, AWR, índices, cursores          |
| Processamento paralelo   | ExecutorService, CompletableFuture            |
| FTP                      | lftp, sftp, AWS Transfer Family               |
| Streaming                | Kinesis Firehose, Lambda, S3, Step Functions  |
| Observabilidade          | Prometheus, Grafana, CloudWatch               |

---

### 🧠 Conclusão

Atuar com foco em eficiência operacional e modernização contínua. Em processos batch legados, otimizações pontuais em consultas SQL, lógica de execução e envio FTP, garantindo performance e estabilidade. Sempre que o contexto permite, proponho a evolução para arquiteturas orientadas a eventos e streaming com AWS (Kinesis, Firehose, S3), mantendo compatibilidade com sistemas legados por meio de soluções híbridas — como o uso do AWS Transfer Family para exposição de arquivos em S3 via FTP. Essa abordagem permite entregar valor imediato, sem comprometer a sustentabilidade futura da arquitetura.
