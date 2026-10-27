package backend_ecocode.Util;

public class CifraAfim {
    private Configuracao configuracao = new  Configuracao();
    private Aritmetica aritmetica = new Aritmetica();

    private static final String ALFABETO = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALFABETO_MINUSCULO = ALFABETO.toLowerCase();
    private final int chave_a = configuracao.getChaveCifraAfimA();
    private final int chave_b = configuracao.getChaveCifraAfimB();

    private String alfabetoDe(char caractere) {
        if (Character.isLowerCase(caractere)) return ALFABETO_MINUSCULO;
        return ALFABETO;
    }

    // letra -> valor (A = 0 ... Z = 25); devolve -1 se não for letra
    private int letraEmValor(char caractere) {
        return alfabetoDe(caractere).indexOf(caractere);
    }

    // valor -> caractere, no alfabeto informado
    private char valorEmLetra(int valor, String alfabeto) {
        if (valor < 0 || valor >= alfabeto.length())
            throw new IllegalArgumentException("Valor fora do alfabeto: " + valor);

        return alfabeto.charAt(valor);
    }

    public String cifraAfim(String mensagem){
        return calcularMensagemAfim(mensagem);
    }

    private String calcularMensagemAfim(String mensagem){
        if (mensagem == null)
            throw new IllegalArgumentException("A mensagem é obrigatória");

        StringBuilder resultado = new StringBuilder();

        for(char caractere : mensagem.toCharArray()){
            int valor = letraEmValor(caractere);

            // não é letra (espaço, número, pontuação, acento): passa sem mudar
            if (valor == -1) {
                resultado.append(caractere);
                continue;
            }

            // calculo do novo valor da letra
            int valorAfim = Math.floorMod(chave_a * valor + chave_b, 26);

            resultado.append(valorEmLetra(valorAfim, alfabetoDe(caractere)));
        }
        return resultado.toString();
    }

    public String descriptografarMensagemAfim(String mensagem){
        int valor_condicao = Aritmetica.inversoModular(chave_a, 26);

        if (mensagem == null)
            throw new IllegalArgumentException("A mensagem é obrigatória");

        StringBuilder resultado = new StringBuilder();

        for(char caractere : mensagem.toCharArray()){
            int valor = letraEmValor(caractere);

            // não é letra (espaço, número, pontuação, acento): passa sem mudar
            if (valor == -1) {
                resultado.append(caractere);
                continue;
            }

            int valorDescriptografado = Math.floorMod(valor_condicao * (valor - chave_b),26);

            resultado.append(valorEmLetra(valorDescriptografado, alfabetoDe(caractere)));


        }

        return resultado.toString();
    }

}
