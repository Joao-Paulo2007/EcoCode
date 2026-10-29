package backend_ecocode.TelaTerminal;

import backend_ecocode.Domain.CargoUsuario;
import backend_ecocode.Domain.Usuario;
import backend_ecocode.Exceptions.AcessoNegadoException;
import backend_ecocode.Service.GerenciarUsuariosService;

import java.util.List;
import java.util.Scanner;

// opções de usuários, usadas só pelo gerente
public class TelaGerenciarUsuariosTerminal {

    private final Scanner sc;
    private final Usuario usuarioLogado;
    private final GerenciarUsuariosService service = new GerenciarUsuariosService();

    // recebe o mesmo Scanner das outras telas, para não abrir outro no System.in
    public TelaGerenciarUsuariosTerminal(Scanner sc, Usuario usuarioLogado) {
        this.sc = sc;
        this.usuarioLogado = usuarioLogado;
    }

    public void cadastrarUsuario() {
        System.out.println("\n--- CADASTRO DE USUÁRIO ---");

        System.out.print("Nome completo: ");
        String nome = sc.nextLine();

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("E-mail: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        System.out.print("Data de nascimento (DD/MM/AAAA): ");
        String dataNascimento = sc.nextLine();

        CargoUsuario cargo = lerCargo();
        if (cargo == null) {
            System.out.println("\nCargo inválido. Cadastro cancelado.");
            return;
        }

        try {
            Usuario novo = service.cadastrar(
                    usuarioLogado.getCargoUsuario(), cpf, nome, email, senha, cargo, dataNascimento
            );
            System.out.println("\nUsuário " + novo.getNomeCompleto() + " cadastrado!");

        } catch (IllegalArgumentException | AcessoNegadoException e) {
            System.out.println("\nNão foi possível cadastrar: " + e.getMessage());
        }
    }

    public void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");

        List<Usuario> usuarios = service.listar();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        // a senha não é mostrada, nem cifrada
        for (Usuario usuario : usuarios) {
            System.out.println("-------------------------");
            System.out.println("Nome: " + usuario.getNomeCompleto());
            System.out.println("CPF: " + usuario.getCpf());
            System.out.println("E-mail: " + usuario.getEmail());
            System.out.println("Cargo: " + usuario.getCargoUsuario());
            System.out.println("Nascimento: " + usuario.getDataNascimento());
        }

        System.out.println("-------------------------");
        System.out.println(usuarios.size() + " usuário(s) cadastrado(s).");
    }

    private CargoUsuario lerCargo() {
        System.out.println("Cargo:");
        System.out.println("[1] - Gerente");
        System.out.println("[2] - Funcionário");
        System.out.print("Digite o número da opção: ");

        String opcao = sc.nextLine().trim();

        if (opcao.equals("1")) return CargoUsuario.GERENTE;
        if (opcao.equals("2")) return CargoUsuario.FUNCIONARIO;
        return null;
    }
}