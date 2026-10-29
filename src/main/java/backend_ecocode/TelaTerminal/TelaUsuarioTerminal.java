package backend_ecocode.TelaTerminal;

import backend_ecocode.Domain.Usuario;
import backend_ecocode.Exceptions.AcessoNegadoException;
import backend_ecocode.Util.LogarUsuario;

import java.util.Scanner;

// tela de login: só deixa passar quem acertar e-mail e senha
public class TelaUsuarioTerminal {

    // único Scanner do programa; é repassado para as outras telas
    private final Scanner sc = new Scanner(System.in);
    private final LogarUsuario logar = new LogarUsuario();

    public void telaPrincipalUsuario() {
        Usuario usuarioLogado = null;

        while (usuarioLogado == null) {
            System.out.println("\n--- FAÇA O LOGIN ---");
            System.out.print("Informe o seu email: ");
            String email = sc.nextLine();
            System.out.print("Informe a sua senha: ");
            String senha = sc.nextLine();

            try {
                usuarioLogado = logar.logarUsuario(email, senha);
            } catch (IllegalArgumentException | AcessoNegadoException e) {
                // mesma mensagem para e-mail ou senha errados, para não revelar quais e-mails existem
                System.out.println("Falha no Login, verifique o email e senha digitados");
            }
        }

        System.out.println("\nLogin realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getNomeCompleto());

        TelaCadastroResiduoTerminal telaResiduo = new TelaCadastroResiduoTerminal(sc, usuarioLogado);
        telaResiduo.telaResiduo();
    }
}