package backend_ecocode.Util;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuracao {

    private final Dotenv dotenv = Dotenv.load();

    public int getChaveCifraCesar() {
        return lerChave("CIFRA_CESAR_CHAVE");
    }

    public int getChaveCifraAfimA() {
        int a = lerChave("CIFRA_AFIM_A");

        // a precisa ser coprimo com 26: ímpar e diferente de 13
        if (a % 2 == 0 || a == 13)
            throw new IllegalStateException(
                    "CIFRA_AFIM_A inválida: use 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23 ou 25");

        return a;
    }

    public int getChaveCifraAfimB() {
        return lerChave("CIFRA_AFIM_B");
    }

    public int getValorCondicaoDescriptografia() {
        return lerChave("VALOR_CONDICAO");
    }


    // lê a variável, confere se é um inteiro e devolve entre 0 e 25
    private int lerChave(String nome) {
        String valor = obrigatoria(nome);

        if (!valor.matches("-?\\d+"))
            throw new IllegalStateException(nome + " deve ser um número inteiro");

        return Math.floorMod(Integer.parseInt(valor), 26);
    }

    // lê a variável e avisa claramente se ela não existir
    private String obrigatoria(String nome) {
        String valor = dotenv.get(nome);

        if (valor == null || valor.isBlank())
            throw new IllegalStateException("Variável " + nome + " não definida no .env");

        return valor.trim();
    }
}