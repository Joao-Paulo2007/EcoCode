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
7. Sair

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

<<<<<<< HEAD
Só o último código gerado (de qualquer tipo) fica salvo, em `arquivos/codigo.json`. O próximo código é o menor número depois dele cujo resto da divisão por 5 é o resto do tipo:

```text
candidato = último + 1
distância = (resto do tipo − candidato) mod 5
próximo   = candidato + distância
```

Exemplo, começando sem arquivo: Celular → 0001, Computador → 0005, Computador → 0010, Bateria → 0013, Outros → 0014, Cabos → 0017. Assim o código também indica a ordem de cadastro, e a sequência continua entre execuções. Detalhes no [ADR-032](docs/ADR-032.md).
=======
O próximo código de um tipo é sempre o último gerado + 5. O último código de cada tipo fica salvo em `arquivos/codigo.json`, então a sequência continua entre execuções.
>>>>>>> 1039930fee32b33634c288d4d25011ad5fce7f1a

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
java -cp "target/classes:target/dependency/*" backend_ecocode.Main
```

No Windows, troque `:` por `;` no classpath: `"target/classes;target/dependency/*"`.

<<<<<<< HEAD
Rode sempre a partir da raiz do projeto: os arquivos de dados são lidos pelo caminho relativo `arquivos/`. A pasta não vai para o Git e é criada na primeira execução, se não existir.
=======
Rode sempre a partir da raiz do projeto: os arquivos de dados são lidos pelo caminho relativo `arquivos`.
>>>>>>> 1039930fee32b33634c288d4d25011ad5fce7f1a

## Estrutura do projeto

```text
.
├── arquivos/                    dados salvos em JSON (fora do Git)
│   ├── rediduos.json            resíduos cadastrados
│   ├── usuarios.json            usuários (senha cifrada)
│   └── codigo.json              último código gerado (de qualquer tipo)
├── docs/                        registro de decisões de arquitetura (ADRs)
├── src/main/java/
│   ├── backend_ecocode.Main.java                menu do terminal
│   ├── backend_ecocode.Teste.java               testes manuais durante o desenvolvimento
│   ├── backend_ecocode.Tela/                    leitura do teclado e exibição
│   ├── backend_ecocode.Service/                 regras de negócio de resíduos e usuários
│   ├── backend_ecocode.Repository/              acesso aos dados (Map em memória + arquivo)
│   ├── backend_ecocode.Domain/                  Residuo, TipoResiduo, Usuario, CargoUsuario e interfaces
│   ├── backend_ecocode.Persistency/             gravação e leitura dos arquivos JSON
│   ├── backend_ecocode.Exceptions/              AcessoNegadoException
│   └── backend_ecocode.Util/                    cifra, geração de código, cálculo do resto, configuração e validações
├── .env.example                 modelo da configuração
└── pom.xml
```

## Arquitetura

O projeto é dividido em camadas:

```text
backend_ecocode.Tela  →  backend_ecocode.Service  →  backend_ecocode.Repository  →  Persistência (JSON)
              ↘          ↘
                 backend_ecocode.Domain (entidades, enums e interfaces)
```

- **backend_ecocode.Tela** só conversa com o usuário: lê o teclado e mostra resultados.
- **backend_ecocode.Service** aplica as regras (gerar código, validar quantidade, checar permissão de gerente, cifrar senha).
- **backend_ecocode.Repository** guarda os dados num `Map` em memória e grava no arquivo a cada alteração.
- **backend_ecocode.Domain** define as entidades e as interfaces que backend_ecocode.Service e backend_ecocode.Repository implementam.
- Erros de validação são sinalizados com exceções (`IllegalArgumentException`, `AcessoNegadoException`) e tratados na tela.

## Documentação

<<<<<<< HEAD
As decisões de arquitetura estão em [`docs/`](docs/README.md), no formato ADR. Lá está o histórico desde a versão 1.0 (tudo numa classe, lista em memória) até a versão atual em camadas com persistência em JSON. Para registrar uma nova decisão, use o [modelo de ADR](docs/ADR-template.md).
=======
As decisões de arquitetura estão em [`docs`](docs/README.md), no formato ADR. Lá está o histórico desde a versão 1.0 (tudo numa classe, lista em memória) até a versão atual em camadas com persistência em JSON. Para registrar uma nova decisão, use o [modelo de ADR](docs/ADR-template.md).
>>>>>>> 1039930fee32b33634c288d4d25011ad5fce7f1a
