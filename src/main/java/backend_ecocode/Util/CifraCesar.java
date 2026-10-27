package backend_ecocode.Util;

import java.util.ArrayList;
import java.util.List;

public class CifraCesar {

    private final Configuracao configuracao = new Configuracao();

    private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALFABETO_MINUSCULO = ALFABETO.toLowerCase();
    private static final String NUMEROS = "0123456789";

    private final int chave = configuracao.getChaveCifraCesar();

    // escolhe em qual "alfabeto" o caractere está: dígitos, minúsculas ou maiúsculas
    private String alfabetoDe(char caractere) {
        if (Character.isDigit(caractere)) return NUMEROS;
        if (Character.isLowerCase(caractere)) return ALFABETO_MINUSCULO;
        return ALFABETO;
    }

    // caractere -> valor (A = 0 ... Z = 25, 0 = 0 ... 9 = 9); devolve -1 se não estiver em nenhum alfabeto
    public int letraEmValor(char caractere) {
        return alfabetoDe(caractere).indexOf(caractere);
    }

    // valor -> caractere, no alfabeto informado
    public char valorEmLetra(int valor, String alfabeto) {
        if (valor < 0 || valor >= alfabeto.length())
            throw new IllegalArgumentException("Valor fora do alfabeto: " + valor);

        return alfabeto.charAt(valor);
    }

    // E(x) = (x + k) mod 26 nas maiúsculas, (x - k) mod 26 nas minúsculas e (x + k) mod 10 nos dígitos
    public String cifraCesar(String mensagem) {
        return deslocar(mensagem, chave);
    }

    // caminho oposto: o mesmo deslocamento com o sinal invertido
    public String descriptografarCifra(String mensagem) {
        return deslocar(mensagem, -chave);
    }

    // força bruta: tenta decifrar com cada chave possível; a posição na lista é a chave
    public List<String> descriptografarTodasChaves(String mensagem) {
        List<String> resultadoTodasChaves = new ArrayList<>();

        for (int i = 0; i < ALFABETO.length(); i++) {
            resultadoTodasChaves.add(deslocar(mensagem, -i));
        }

        return resultadoTodasChaves;
    }

    // único lugar que faz a conta: anda "deslocamento" casas em cada caractere
    private String deslocar(String mensagem, int deslocamento) {
        if (mensagem == null)
            throw new IllegalArgumentException("A mensagem é obrigatória");

        StringBuilder resultado = new StringBuilder();

        for (char caractere : mensagem.toCharArray()) {

            int valor = letraEmValor(caractere);

            // não é letra nem dígito (espaço, pontuação, acento): passa sem mudar
            if (valor == -1) {
                resultado.append(caractere);
                continue;
            }

            String alfabeto = alfabetoDe(caractere);

            // minúsculas andam no sentido contrário; maiúsculas e dígitos andam para a frente
            int sentido = Character.isLowerCase(caractere) ? -1 : 1;
            int novoValor = Math.floorMod(valor + sentido * deslocamento, alfabeto.length());

            resultado.append(valorEmLetra(novoValor, alfabeto));
        }

        return resultado.toString();
    }
}