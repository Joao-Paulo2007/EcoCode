package Util;

public class ValidadorCpf {

    /** Remove tudo que não for dígito. Não valida nada. */
    public static String limpar(String cpf) {
        if (cpf == null) {
            return "";
        }
        return cpf.replaceAll("\\D", "");
    }

    /** Retorna true se o CPF for válido, com ou sem pontuação. */
    public static boolean ehValido(String cpf) {
        String numeros = limpar(cpf);

        if (numeros.length() != 11) {
            return false;
        }

        // 111.111.111-11 passa na conta, mas não é um CPF válido
        if (numeros.matches("(\\d)\\1{10}")) {
            return false;
        }

        int digito1 = calcularDigito(numeros, 9);
        int digito2 = calcularDigito(numeros, 10);

        return digito1 == Character.getNumericValue(numeros.charAt(9))
                && digito2 == Character.getNumericValue(numeros.charAt(10));
    }

    /** Valida e devolve o CPF só com dígitos. Lança exceção se for inválido. */
    public static String exigirValido(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("Informe um CPF.");
        }
        if (!ehValido(cpf)) {
            throw new IllegalArgumentException("Informe um CPF válido.");
        }
        return limpar(cpf);
    }

    /** Calcula um dígito verificador usando os primeiros 'quantidade' dígitos. */
    private static int calcularDigito(String numeros, int quantidade) {
        int soma = 0;
        int peso = quantidade + 1;

        for (int i = 0; i < quantidade; i++) {
            soma += Character.getNumericValue(numeros.charAt(i)) * peso;
            peso--;
        }

        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}