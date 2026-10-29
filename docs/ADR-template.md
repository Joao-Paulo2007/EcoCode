# ADR-000: [Título curto da decisão, no imperativo]

- **Status:** [Proposta | Aceita | Rejeitada | Substituída por ADR-XXX | Obsoleta]
- **Data:** AAAA-MM-DD
- **Autores:** [nomes do grupo]
- **Revisores:** [quem aprovou ou revisou]

## Contexto

Descreva a situação que exige uma decisão. Qual é o problema, qual requisito ou restrição
apareceu, e por que isso precisa ser decidido agora.

- O que já existe no projeto e é afetado.
- Requisitos do enunciado, do cliente ou da disciplina que pesam aqui.
- Restrições: prazo, tecnologia, conhecimento do grupo, tamanho do projeto.

## Decisão

Escreva a decisão em uma ou duas frases diretas, começando com "Vamos...".

> Vamos [o que foi decidido], porque [motivo principal].

Detalhe como a decisão será aplicada, se necessário: em quais classes, pacotes ou camadas.

## Alternativas consideradas

### Alternativa 1: [nome]

- **Prós:** ...
- **Contras:** ...
- **Por que não foi escolhida:** ...

### Alternativa 2: [nome]

- **Prós:** ...
- **Contras:** ...
- **Por que não foi escolhida:** ...

## Consequências

### Positivas

- O que fica mais fácil ou melhor com essa decisão.

### Negativas

- O que fica mais difícil, o custo assumido, a dívida técnica criada.

### Neutras

- Mudanças que não são boas nem ruins, mas que o grupo precisa saber (convenções novas,
  arquivos que passam a existir, etc.).

## Riscos e pendências

- [ ] Risco ou ponto em aberto, e o que fazer se ele acontecer.
- [ ] Condição que, se mudar, faz essa decisão ser revista.

## Referências

- Links, trechos do enunciado, ADRs relacionados (ex.: ADR-001), documentação consultada.

---

<!--
Como usar este template:

1. Copie este arquivo para docs/adr/ com o nome NNN-titulo-em-kebab-case.md
   (ex.: 001-usar-map-no-repositorio.md). A numeração é sequencial e nunca é reaproveitada.
2. Um ADR registra UMA decisão. Se surgirem duas, faça dois ADRs.
3. Um ADR aceito não é editado depois. Se a decisão mudar, crie um ADR novo e marque o
   antigo como "Substituída por ADR-XXX".
4. Seções opcionais que não se aplicarem (Revisores, Neutras, Riscos) podem ser apagadas.
-->