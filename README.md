# 🏧 Projeto Caixa Eletrônico

Programa desenvolvido em Java para simular o funcionamento de um caixa eletrônico, controlando o estoque de notas e realizando saques de forma otimizada, com interface gráfica Swing.

---

## 📋 Descrição do Projeto

O sistema simula um caixa eletrônico com 6 tipos de notas disponíveis: **R$ 2, R$ 5, R$ 10, R$ 20, R$ 50 e R$ 100**. O programa entra em operação contínua atendendo clientes, gerencia o estoque de notas a cada saque realizado e permite ao administrador realizar reposições e configurar a cota mínima de operação.

---

## ⚙️ Funcionalidades

### Módulo do Cliente
- **Efetuar Saque** — o cliente informa o valor desejado e o sistema entrega as notas de forma otimizada

### Módulo do Administrador
- **Relatório de Cédulas** — exibe a quantidade disponível de cada tipo de nota
- **Valor Total Disponível** — exibe o saldo total em reais no caixa
- **Reposição de Cédulas** — permite adicionar notas ao estoque do caixa
- **Cota Mínima** — define o valor mínimo que o caixa precisa ter para continuar operando

### Módulo de Ambos
- **Sair** — encerra o atendimento e exibe o extrato completo de todas as operações realizadas

---

## 🧠 Lógica de Saque

O programa sempre tenta pagar com as **maiores notas possíveis**, seguindo a ordem de prioridade:

```
R$ 100 → R$ 50 → R$ 20 → R$ 10 → R$ 5 → R$ 2
```

Antes de confirmar o saque, o sistema:

1. Simula o pagamento em um array temporário sem alterar o estoque real
2. Verifica se é possível atender ao valor exato com as notas disponíveis
3. Verifica se o número de notas não ultrapassa o **limite de 30 cédulas por operação**
4. Só desconta o estoque após todas as validações passarem

Mensagens de controle:

| Situação | Mensagem exibida |
|---|---|
| Notas insuficientes para o valor | `Saque não realizado por falta de cédulas` |
| Mais de 30 cédulas necessárias | `Saque não realizado: excede o limite de 30 cédulas por operação` |
| Caixa abaixo da cota mínima | `Caixa Vazio: Chame o Operador` |

---

## 💾 Estoque Inicial

O caixa inicia com as seguintes quantidades, conforme especificação do projeto:

| Cédula | Quantidade inicial |
|---|---|
| R$ 100 | 100 |
| R$ 50  | 200 |
| R$ 20  | 300 |
| R$ 10  | 350 |
| R$ 5   | 450 |
| R$ 2   | 500 |

---

## 🖥️ Interface Gráfica

A interface foi desenvolvida em **Java Swing** e segue o layout especificado no enunciado do projeto. A `GUI` se comunica com a lógica do caixa exclusivamente através da interface `ICaixaEletronico`, garantindo o desacoplamento entre tela e regras de negócio.

Ao clicar em **Sair**, o sistema exibe automaticamente um extrato com todas as operações realizadas na sessão (saques e reposições).

---

## 📁 Estrutura do Projeto

```
caixa-eletronico-java/
├── src/
│   └── caixaeletronico/
│       ├── CaixaEletronico.java    # Lógica principal do caixa eletrônico
│       ├── ICaixaEletronico.java   # Interface (contrato) fornecida pelo professor
│       └── GUI.java                # Interface gráfica Swing
├── .gitignore
└── README.md
```

---

## 🛠️ Tecnologias

- Java JDK 8+
- Java Swing (interface gráfica)

---

## ▶️ Como Executar

1. Clone o repositório
2. Abra o projeto no **IntelliJ IDEA** (ou outra IDE Java)
3. Execute o método `main` da classe `CaixaEletronico`
4. A janela do caixa eletrônico será aberta automaticamente

---

## 📌 Observações

- Projeto desenvolvido como exercício acadêmico da disciplina de **Programação Orientada a Objetos**
- A interface `ICaixaEletronico` foi fornecida pelo professor e não pode ser alterada
- A classe `GUI` implementa a tela seguindo o contrato definido na interface
- O extrato exibido ao sair registra todos os saques e reposições da sessão
