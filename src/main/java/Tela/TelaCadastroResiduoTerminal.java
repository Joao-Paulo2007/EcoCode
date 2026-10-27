package Tela;

import Domain.TipoResiduo;
import Domain.Residuo;
import Service.GerenciarResiduosService;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TelaCadastroResiduoTerminal {

    private Scanner sc;
    private final GerenciarResiduosService service = new GerenciarResiduosService();

    public TelaCadastroResiduoTerminal(Scanner sc) {
        this.sc = sc;
    }

    public void telaCadastrarResiduo() {

        System.out.println("\n--- CADASTRO DE RESÍDUO ---");

        TipoResiduo tipoSelecionado = lerTipoResiduo();

        if (tipoSelecionado == null) {
            System.out.println("\nOpção inválida. Cadastro cancelado.");
            return;
        }

        System.out.print("Qual a quantidade? ");
        int quantidade = sc.nextInt();
        sc.nextLine(); // limpa o buffer

        System.out.print("Descrição (opcional, aperte Enter para pular): ");
        String descricao = sc.nextLine();

        try {
            Residuo residuo = service.salvar(tipoSelecionado, quantidade, descricao);

            System.out.println("\nResíduo cadastrado!");
            System.out.println("Código: " + residuo.getCodigo());
            System.out.println("Categoria: " + residuo.getCategoriaResiduo());
            System.out.println("Quantidade: " + residuo.getQuantidade());

            if (!residuo.getDescricao().isEmpty()) {
                System.out.println("Descrição: " + residuo.getDescricao());
            }

        } catch (IllegalArgumentException e) {
            System.out.println("\nNão foi possível cadastrar: " + e.getMessage());
        }
    }

    public void editarResiduo() {

        System.out.println("\n--- EDITAR RESÍDUO ---");
        System.out.print("Digite o código do resíduo: ");
        String codigo = sc.nextLine();

        try {
            Residuo residuo = service.buscar(codigo);

            System.out.println("\nCódigo: " + residuo.getCodigo());
            System.out.println("Categoria: " + residuo.getCategoriaResiduo());
            System.out.println("Quantidade: " + residuo.getQuantidade());
            if (!residuo.getDescricao().isEmpty()) {
                System.out.println("Descrição: " + residuo.getDescricao());
            }

            System.out.print("\nEsse é o resíduo que deseja editar? (s/n): ");
            String resposta = sc.nextLine();

            if (!resposta.equalsIgnoreCase("s")) {
                System.out.println("Edição cancelada.");
                return;
            }

            // Enter vazio mantém o valor atual
            System.out.print("Nova quantidade (Enter para manter " + residuo.getQuantidade() + "): ");
            String quantidadeDigitada = sc.nextLine().trim();

            int quantidade = residuo.getQuantidade();
            if (!quantidadeDigitada.isEmpty()) {
                quantidade = Integer.parseInt(quantidadeDigitada);
            }

            System.out.print("Nova descrição (Enter para manter a atual): ");
            String descricaoDigitada = sc.nextLine();

            String descricao = residuo.getDescricao();
            if (!descricaoDigitada.isBlank()) {
                descricao = descricaoDigitada;
            }

            Residuo editado = service.editar(codigo, quantidade, descricao);

            System.out.println("\nResíduo editado!");
            System.out.println("Código: " + editado.getCodigo());
            System.out.println("Categoria: " + editado.getCategoriaResiduo());
            System.out.println("Quantidade: " + editado.getQuantidade());
            if (!editado.getDescricao().isEmpty()) {
                System.out.println("Descrição: " + editado.getDescricao());
            }

        } catch (NumberFormatException e) {
            System.out.println("\nNão foi possível editar o resíduo: a quantidade deve ser um número.");
        } catch (IllegalArgumentException e) {
            System.out.println("\nNão foi possível editar o resíduo: " + e.getMessage());
        }
    }

    public void listar_residuos() {

        System.out.println("\n--- LISTA DE RESÍDUOS ---");
        System.out.println("1 - Ordem de cadastro");
        System.out.println("2 - Ordem de código");
        System.out.print("Escolha: ");
        String ordem = sc.nextLine();

        List<Residuo> lista = ordem.equals("2") ? service.listarPorCodigo() : service.listar();

        if (lista.isEmpty()) {
            System.out.println("Nenhum resíduo cadastrado.");
            return;
        }

        for (Residuo residuo : lista) {
            System.out.println("-------------------------");
            System.out.println("Código: " + residuo.getCodigo());
            System.out.println("Resíduo: " + residuo.getCategoriaResiduo());
            System.out.println("Quantidade: " + residuo.getQuantidade());
            System.out.println("Descrição: " + residuo.getDescricao());
        }

        System.out.println("-------------------------");
    }

    // mostra o menu de tipos e devolve o escolhido, ou null se a opção não existir
    private TipoResiduo lerTipoResiduo() {

        System.out.println("Selecione o tipo de resíduo eletrônico:");
        for (TipoResiduo tipo : TipoResiduo.values()) {
            System.out.println("[" + tipo.getResto() + "] - " + tipo.getDescricao());
        }

        System.out.print("Digite o número da opção: ");
        byte opcaoDigitada = sc.nextByte();
        sc.nextLine(); // limpa o buffer

        for (TipoResiduo tipo : TipoResiduo.values()) {
            if (tipo.getResto() == opcaoDigitada) {
                return tipo;
            }
        }
        return null;
    }

    public void listarCategoriaResiduo() {

        System.out.println("\n--- LISTAR POR CATEGORIA ---");

        TipoResiduo tipoSelecionado = lerTipoResiduo();

        if (tipoSelecionado == null) {
            System.out.println("\nOpção inválida.");
            return;
        }

        List<Residuo> lista = service.listarPorTipo(tipoSelecionado);

        System.out.println("\nCategoria: " + tipoSelecionado.getDescricao());

        if (lista.isEmpty()) {
            System.out.println("Nenhum resíduo cadastrado nessa categoria.");
            return;
        }

        int total = 0;

        for (Residuo residuo : lista) {
            System.out.println("-------------------------");
            System.out.println("Código: " + residuo.getCodigo());
            System.out.println("Quantidade: " + residuo.getQuantidade());

            if (!residuo.getDescricao().isEmpty()) {
                System.out.println("Descrição: " + residuo.getDescricao());
            }

            total += residuo.getQuantidade();
        }

        System.out.println("-------------------------");
        System.out.println(lista.size() + " cadastro(s), " + total + " unidade(s) no total.");
    }

    public void excluirResiduo() {

        System.out.println("\n--- EXCLUIR RESÍDUO ---");
        System.out.print("Digite o código do resíduo: ");
        String codigo = sc.nextLine();

        try {
            Residuo residuo = service.buscar(codigo);

            System.out.println("\nCódigo: " + residuo.getCodigo());
            System.out.println("Categoria: " + residuo.getCategoriaResiduo());
            System.out.println("Quantidade: " + residuo.getQuantidade());

            System.out.print("\nTem certeza que deseja excluir? (s/n): ");
            String resposta = sc.nextLine();

            if (!resposta.equalsIgnoreCase("s")) {
                System.out.println("Exclusão cancelada.");
                return;
            }

            service.excluir(codigo);
            System.out.println("Resíduo excluído!");

        } catch (IllegalArgumentException e) {
            System.out.println("\nNão foi possível excluir: " + e.getMessage());
        }
    }
}