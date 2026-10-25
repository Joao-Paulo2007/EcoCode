package Util;

import io.github.cdimascio.dotenv.Dotenv;

public class Configuracao {

    private final Dotenv dotenv = Dotenv.load();

    public int getChaveCifra() {
        String valor = obrigatoria("CHAVE_CIFRA");

        if (!valor.matches("-?\\d+"))
            throw new IllegalStateException("CHAVE_CIFRA deve ser um número inteiro");

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