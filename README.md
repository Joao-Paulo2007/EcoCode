# EcoCode — Recicla+

> *Menos lixo e um mundo melhor.*

Aplicação de terminal em Java para cadastrar e acompanhar **resíduos eletrônicos** destinados à reciclagem. Cada resíduo recebe um código numérico que já indica a sua categoria, os dados ficam salvos em arquivos JSON e há um módulo de usuários com senhas cifradas pela Cifra de César.

## Funcionalidades

**Resíduos** (menu do terminal)

1. Cadastrar um resíduo (tipo, quantidade e descrição opcional)
2. Listar todos os resíduos
3. Ver a quantidade de cadastros
4. Listar os resíduos de uma categoria, com o total de unidades
5. Excluir um resíduo pelo código, com confirmação
6. Editar a quantidade e a descrição de um resíduo

**Usuários** (camada de service, ainda sem tela no menu)

- Cadastrar, buscar, listar, editar e remover usuários, usando o CPF como chave
- Apenas usuários com cargo `GERENTE` podem cadastrar ou remover usuários
- Validação de CPF, e-mail, nome e data de nascimento (`DD/MM/AAAA`)
- Senha guardada cifrada com a Cifra de César

## Categorias e código do resíduo

O código tem 4 dígitos e é gerado de forma que **`código mod 5`** seja igual ao número da categoria:

| Resto | Categoria | Exemplos de código |
| --- | --- | --- |
| 0 | Computadores e Notebooks | 0005, 0010, 0015 |
| 1 | Celulares e Tablets | 0001, 0006, 0011 |
| 2 | Cabos e Periféricos | 0002, 0007, 0012 |
| 3 | Pilhas e Baterias | 0003, 0008, 0013 |
| 4 | Outros Eletrônicos | 0004, 0009, 0014 |

O próximo código de um tipo é sempre o último gerado + 5. O último código de cada tipo fica salvo em `arquivos/codigo.json`, então a sequência continua entre execuções.

## Cifra de César

A classe `Util/CifraCesar` cifra as senhas com uma chave lida do arquivo `.env`:

- Maiúsculas: `E(x) = (x + k) mod 26`
- Minúsculas: `E(x) = (x − k) mod 26` (andam no sentido contrário)
- Dígitos: `E(x) = (x + k) mod 10`
- Espaços, pontuação e acentos não mudam

Também há a decifração (`descriptografarCifra`) e a força bruta com todas as chaves (`descriptografarTodasChaves`).

## Tecnologias

- Java 25
- Maven
- [Gson](https://github.com/google/gson) 2.12.1 — leitura e escrita dos arquivos JSON
- [dotenv-java](https://github.com/cdimascio/dotenv-java) 3.0.0 — leitura da chave da cifra

## Como executar

### Pré-requisitos

- JDK 25
- Maven 3.9+ (ou uma IDE como IntelliJ IDEA)

### 1. Configurar a chave da cifra

Copie o arquivo de exemplo e coloque um número inteiro como chave:

```bash
cp .env.example .env
```

```env
CHAVE_CIFRA=3
```

O `.env` não vai para o Git. Sem ele, o programa para com o erro `Variável CHAVE_CIFRA não definida no .env`.

> A chave precisa ser a mesma usada quando as senhas de `arquivos/usuarios.json` foram cifradas; com outra chave, elas não serão decifradas corretamente.

### 2. Compilar e rodar

Pela IDE, rode a classe `Main` (em `src/main/java/Main.java`).

Pelo terminal, a partir da raiz do projeto:

```bash
mvn compile
mvn dependency:copy-dependencies
java -cp "target/classes:target/dependency/*" Main
```

No Windows, troque `:` por `;` no classpath: `"target/classes;target/dependency/*"`.

Rode sempre a partir da raiz do projeto: os arquivos de dados são lidos pelo caminho relativo `arquivos`.

## Estrutura do projeto

```text
.
├── arquivos/                    dados salvos em JSON
│   ├── rediduos.json            resíduos cadastrados
│   ├── usuarios.json            usuários (senha cifrada)
│   └── codigo.json              último código gerado de cada tipo
├── docs/                        registro de decisões de arquitetura (ADRs)
├── src/main/java/
│   ├── Main.java                menu do terminal
│   ├── Tela/                    leitura do teclado e exibição
│   ├── Service/                 regras de negócio de resíduos e usuários
│   ├── Repository/              acesso aos dados (Map em memória + arquivo)
│   ├── Domain/                  Residuo, TipoResiduo, Usuario, CargoUsuario e interfaces
│   ├── Persistency/             gravação e leitura dos arquivos JSON
│   ├── Exceptions/              AcessoNegadoException
│   └── Util/                    cifra, geração de código, configuração e validações
├── .env.example                 modelo da configuração
└── pom.xml
```

## Arquitetura

O projeto é dividido em camadas:

```text
Tela  →  Service  →  Repository  →  Persistência (JSON)
              ↘          ↘
                 Domain (entidades, enums e interfaces)
```

- **Tela** só conversa com o usuário: lê o teclado e mostra resultados.
- **Service** aplica as regras (gerar código, validar quantidade, checar permissão de gerente, cifrar senha).
- **Repository** guarda os dados num `Map` em memória e grava no arquivo a cada alteração.
- **Domain** define as entidades e as interfaces que Service e Repository implementam.
- Erros de validação são sinalizados com exceções (`IllegalArgumentException`, `AcessoNegadoException`) e tratados na tela.

## Documentação

As decisões de arquitetura estão em [`docs`](docs/README.md), no formato ADR. Lá está o histórico desde a versão 1.0 (tudo numa classe, lista em memória) até a versão atual em camadas com persistência em JSON. Para registrar uma nova decisão, use o [modelo de ADR](docs/ADR-template.md).
