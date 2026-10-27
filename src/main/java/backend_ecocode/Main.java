package backend_ecocode;

import java.util.Scanner;

import backend_ecocode.Service.GerenciarResiduosService;
import backend_ecocode.Tela.TelaCadastroResiduoTerminal;

public class Main {

    public static void main(String[] args) {

        GerenciarResiduosService service = new GerenciarResiduosService();

        Scanner sc = new Scanner(System.in);

        TelaCadastroResiduoTerminal cadastro = new TelaCadastroResiduoTerminal(sc);

        int opcao;

        do {

            System.out.println("\n================================");
            System.out.println("RECICLA +");
            System.out.println("(Menos lixo e um mundo melhor)");
            System.out.println("================================");

            System.out.println("1 - Cadastrar um resíduo");
            System.out.println("2 - Verificar lista de resíduos");
            System.out.println("3 - Verificar quantidade de resíduos");
            System.out.println("4 - Classificar resíduos");
            System.out.println("5 - Excluir um resíduo");
            System.out.println("6 - Editar um resíduo");
            System.out.println("7 - Sair");

            System.out.print("\nEscolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    cadastro.telaCadastrarResiduo();
                    break;

                case 2:
                    cadastro.listar_residuos();
                    break;

                case 3:
                    System.out.println("\n--- QUANTIDADE ---");
                    System.out.println("Quantidade de cadastros: " + service.quantidadeResiduosCadastrados());
                    System.out.println("Quantidade total de resíduos: " + service.quantidadeTotalResiduos());
                    break;

                case 4:
                    cadastro.listarCategoriaResiduo();
                    break;

                case 5:
                    cadastro.excluirResiduo();
                    break;

                case 6:
                    cadastro.editarResiduo();
                    break;

                case 7:
                    System.out.println("\nEncerrando o programa...");
                    break;

                default:
                    System.out.println("\nOpção inválida!");
            }
        } while (opcao != 7);
        sc.close();
    }
}