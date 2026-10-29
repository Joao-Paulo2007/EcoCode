# Registro de decisões de arquitetura (ADRs) - EcoCode

Decisões de arquitetura do módulo de resíduos. Um ADR aceito não é editado depois: se a
decisão mudar, cria-se um ADR novo, e o antigo passa a "Substituída por ADR-XXX".

## Versão 1.0

Decisões da versão 1.0, como está no commit "Adicionando versao 1.0" (2026-10-06), antes
da refatoração em camadas. Os nomes de classes citados nestes ADRs são os da versão 1.0
(por exemplo, `Cadastro`), não os atuais.

Estes ADRs foram **reconstruídos a partir do código**. O que o código faz está descrito
com precisão e foi conferido rodando o programa. Os motivos de cada decisão foram
deduzidos do código e do enunciado da Missão 02: o grupo deve revisar a seção
"Contexto" de cada um e corrigir onde o motivo real tiver sido outro.

| ADR | Decisão | Status |
| --- | --- | --- |
| [001](../../adrs-ecocode-v4/adrs-v4/ADR-001.md) | Aplicação de terminal em Java com Maven, sem dependências | Aceita, alterada por [ADR-038](../../adrs-ecocode-v4/adrs-v4/ADR-038.md) |
| [002](../../adrs-ecocode-v4/adrs-v4/ADR-002.md) | Concentrar tela, dados e geração de código na classe `Cadastro` | Substituída por [ADR-010](../../adrs-ecocode-v4/adrs-v4/ADR-010.md) |
| [003](../../adrs-ecocode-v4/adrs-v4/ADR-003.md) | Guardar os resíduos numa `ArrayList` em memória | Substituída por [ADR-011](../../adrs-ecocode-v4/adrs-v4/ADR-011.md) |
| [004](../../adrs-ecocode-v4/adrs-v4/ADR-004.md) | Gerar o código de forma que `código mod 5` caia na categoria do tipo | Aceita |
| [005](../../adrs-ecocode-v4/adrs-v4/ADR-005.md) | Representar o código como `String` de 6 dígitos com zeros à esquerda | Substituída por [ADR-013](../../adrs-ecocode-v4/adrs-v4/ADR-013.md) |
| [006](../../adrs-ecocode-v4/adrs-v4/ADR-006.md) | Receber o tipo do resíduo como texto livre, reconhecido por sinônimos | Substituída por [ADR-012](../../adrs-ecocode-v4/adrs-v4/ADR-012.md) |
| [007](../../adrs-ecocode-v4/adrs-v4/ADR-007.md) | Classificar com uma classe utilitária estática | Substituída por [ADR-015](../../adrs-ecocode-v4/adrs-v4/ADR-015.md) |
| [008](../../adrs-ecocode-v4/adrs-v4/ADR-008.md) | Carregar 12 registros de exemplo no construtor do `Cadastro` | Substituída por [ADR-018](../../adrs-ecocode-v4/adrs-v4/ADR-018.md) |

### Estrutura da versão 1.0

```text
src/main/java/
├── backend_ecocode.Main.java                         menu do terminal
├── Cadastro/Cadastro.java            telas, lista de resíduos, geração de código, seed
├── Classificador/Classificador.java  tipo -> resto, código -> resto -> categoria
└── Resíduo/Residuos.java             entidade (código, tipo, quantidade)
```

## Versão refatorada

Decisões tomadas a partir da refatoração em camadas (tela, service, repository).

| ADR | Decisão | Status |
| --- | --- | --- |
| [009](../../adrs-ecocode-v4/adrs-v4/ADR-009.md) | Adicionar uma descrição opcional aos resíduos | Aceita |
| [010](../../adrs-ecocode-v4/adrs-v4/ADR-010.md) | Separar o módulo em camadas (tela, service, repository e domínio) | Aceita |
| [011](../../adrs-ecocode-v4/adrs-v4/ADR-011.md) | Guardar os resíduos num `TreeMap` com o código como chave | Aceita, alterada por [ADR-024](../../adrs-ecocode-v4/adrs-v4/ADR-024.md) e [ADR-036](../../adrs-ecocode-v4/adrs-v4/ADR-036.md) |
| [012](../../adrs-ecocode-v4/adrs-v4/ADR-012.md) | Representar o tipo do resíduo com o enum `TipoResiduo` | Aceita |
| [013](../../adrs-ecocode-v4/adrs-v4/ADR-013.md) | Gerar o código com 4 dígitos numa classe própria e normalizar o código digitado | Aceita, alterada por [ADR-025](../../adrs-ecocode-v4/adrs-v4/ADR-025.md) e [ADR-032](../../adrs-ecocode-v4/adrs-v4/ADR-032.md) |
| [014](../../adrs-ecocode-v4/adrs-v4/ADR-014.md) | Validar no objeto e no service, sinalizando erros com exceções | Aceita, alterada por [ADR-030](../../adrs-ecocode-v4/adrs-v4/ADR-030.md) |
| [015](../../adrs-ecocode-v4/adrs-v4/ADR-015.md) | Calcular a classe pelo código no service, no lugar do `Classificador` | Aceita |
| [016](../../adrs-ecocode-v4/adrs-v4/ADR-016.md) | Editar e excluir por código, com confirmação e sem alterar tipo nem código | Aceita |
| [017](../../adrs-ecocode-v4/adrs-v4/ADR-017.md) | Filtrar a listagem por categoria no service | Aceita |
| [018](../../adrs-ecocode-v4/adrs-v4/ADR-018.md) | Cadastrar os registros iniciais pelo service | Aceita |
| [019](../../adrs-ecocode-v4/adrs-v4/ADR-019.md) | Implementar a Cifra de César sobre a correspondência A = 0 ... Z = 25 | Aceita, alterada por [ADR-021](../../adrs-ecocode-v4/adrs-v4/ADR-021.md) e [ADR-031](../../adrs-ecocode-v4/adrs-v4/ADR-031.md) |
| [020](../../adrs-ecocode-v4/adrs-v4/ADR-020.md) | Ler a chave da cifra de um arquivo `.env` com `dotenv-java` | Aceita, alterada por [ADR-033](../../adrs-ecocode-v4/adrs-v4/ADR-033.md) |
| [021](../../adrs-ecocode-v4/adrs-v4/ADR-021.md) | Preservar maiúsculas e minúsculas na cifra, deslocando cada caixa num sentido | Aceita, alterada por [ADR-031](../../adrs-ecocode-v4/adrs-v4/ADR-031.md) |
| [022](../../adrs-ecocode-v4/adrs-v4/ADR-022.md) | Criar o módulo de usuários com CPF como chave e cadastro restrito a gerentes | Aceita |
| [023](../../adrs-ecocode-v4/adrs-v4/ADR-023.md) | Guardar a senha do usuário cifrada com a Cifra de César | Aceita |
| [024](../../adrs-ecocode-v4/adrs-v4/ADR-024.md) | Salvar os resíduos em arquivo JSON, com o `Map` em memória como fonte principal | Aceita |
| [025](../../adrs-ecocode-v4/adrs-v4/ADR-025.md) | Gerar o código por sequência de cada tipo (último + 5), salvando o último em arquivo | Substituída por [ADR-032](../../adrs-ecocode-v4/adrs-v4/ADR-032.md) |
| [026](../../adrs-ecocode-v4/adrs-v4/ADR-026.md) | Salvar os usuários em arquivo JSON, reaproveitando a persistência dos resíduos | Aceita |
| [027](../../adrs-ecocode-v4/adrs-v4/ADR-027.md) | Guardar e buscar o CPF só com dígitos | Aceita |
| [028](../../adrs-ecocode-v4/adrs-v4/ADR-028.md) | Reunir as validações do usuário na classe `ValidacoesUsuario` | Aceita |
| [029](../../adrs-ecocode-v4/adrs-v4/ADR-029.md) | Data de nascimento em AAAA-MM-DD no arquivo e em DD/MM/AAAA para o usuário | Aceita |
| [030](../../adrs-ecocode-v4/adrs-v4/ADR-030.md) | Reunir as validações do resíduo na classe `ValidacoesResiduos` | Aceita |
| [031](../../adrs-ecocode-v4/adrs-v4/ADR-031.md) | Cifrar também os dígitos e concentrar a conta da cifra num único método | Aceita |
| [032](../../adrs-ecocode-v4/adrs-v4/ADR-032.md) | Calcular o próximo código a partir do último gerado, de qualquer tipo | Aceita |
| [033](../../adrs-ecocode-v4/adrs-v4/ADR-033.md) | Implementar a Cifra Afim com as chaves a e b no `.env` | Aceita, alterada por [ADR-035](../../adrs-ecocode-v4/adrs-v4/ADR-035.md) |
| [034](../../adrs-ecocode-v4/adrs-v4/ADR-034.md) | Reunir MDC, MMC e inverso modular na classe `Aritmetica` | Aceita |
| [035](../../adrs-ecocode-v4/adrs-v4/ADR-035.md) | Calcular o inverso da Cifra Afim pelo Euclides Estendido | Aceita |
| [036](../../adrs-ecocode-v4/adrs-v4/ADR-036.md) | Listar os resíduos por ordem de cadastro ou por ordem de código | Aceita |
| [037](../../adrs-ecocode-v4/adrs-v4/ADR-037.md) | Calcular as quantidades de resíduos no service | Aceita |

## Versão com Spring Boot e front-end

Decisões tomadas quando o projeto ganhou um servidor web e as telas para o navegador.

| ADR | Decisão | Status |
| --- | --- | --- |
| [038](../../adrs-ecocode-v4/adrs-v4/ADR-038.md) | Usar Spring Boot como servidor do back-end | Aceita |
| [039](../../adrs-ecocode-v4/adrs-v4/ADR-039.md) | Colocar todo o código Java dentro do pacote raiz `backend_ecocode` | Aceita |
| [040](../../adrs-ecocode-v4/adrs-v4/ADR-040.md) | Marcar services e repositórios como componentes do Spring e sincronizar os repositórios | Aceita |
| [041](../../adrs-ecocode-v4/adrs-v4/ADR-041.md) | Fazer o front-end em HTML, CSS e JavaScript puros dentro de `resources` | Aceita |
| [042](ADR-042.md) | Manter a persistência em arquivos JSON e adiar o banco de dados | Aceita |
| [043](ADR-043.md) | Fazer o login por e-mail e senha, devolvendo o usuário que entrou | Aceita |
| [044](ADR-044.md) | Garantir e-mail único conferindo no service | Aceita |
| [045](ADR-045.md) | Mostrar as opções de usuários no menu só para o gerente, numa tela própria | Aceita |
| [046](ADR-046.md) | Usar um único `Scanner` para o terminal e ler os números linha por linha | Aceita |

### Estrutura atual

```text
src/main/java/backend_ecocode/
├── ServidorApplication.java      entrada do servidor Spring Boot
├── Main.java                     entrada do menu do terminal
├── Controller/                   TesteController
├── TelaTerminal/                 TelaUsuarioTerminal (login), TelaCadastroResiduoTerminal, TelaGerenciarUsuariosTerminal
├── Service/                      GerenciarResiduosService, GerenciarUsuariosService
├── Repository/                   GerenciarResiduosRepository, GerenciarUsuariosRepository
├── Domain/                       Residuo, TipoResiduo, Usuario, CargoUsuario e as interfaces
├── Persistency/                  Persistencia e Files/PersistenciaArquivo
├── Exceptions/                   AcessoNegadoException
└── Util/                         GerarCodigoResiduo, CifraCesar, CifraAfim, Aritmetica, Configuracao, Constantes, LogarUsuario, ValidadorCpf, ValidarEmail, ValidacoesUsuario, ValidacoesResiduos

src/main/resources/frontend_ecocode/
├── html/                         index, login, cadastro, Tela
├── css/                          base e um arquivo por tela
└── js/                           tela.js
```

Dados salvos em `arquivos/` na raiz do projeto: `residuos.json`, `usuarios.json` e o arquivo do último código gerado (`CODIGO_PATH`).
