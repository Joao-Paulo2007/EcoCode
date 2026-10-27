package backend_ecocode.Util;

// Teoria dos Números usada nas cifras: MDC, MMC e inverso modular (Missão 03, Etapa 7)
public class Aritmetica {

    // MDC pelo Algoritmo de Euclides: troca (a, b) por (b, a mod b) até b virar 0
    public static int mdc(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            int resto = a % b;
            a = b;
            b = resto;
        }
        return a;
    }

    // MMC a partir do MDC: mmc(a, b) = a × b / mdc(a, b)
    public static int mmc(int a, int b) {
        if (a == 0 || b == 0) return 0;

        // divide antes de multiplicar, para não estourar o int
        return Math.abs(a / mdc(a, b) * b);
    }

    // a tem inverso módulo n quando a e n são coprimos (MDC = 1)
    public static boolean possuiInverso(int a, int n) {
        return mdc(a, n) == 1;
    }

    // Inverso de a módulo n pelo Algoritmo de Euclides Estendido:
    // devolve o x, entre 0 e n - 1, tal que (a · x) mod n = 1
    public static int inversoModular(int a, int n) {
        if (n <= 1)
            throw new IllegalArgumentException("O módulo deve ser maior que 1");

        a = Math.floorMod(a, n);

        if (!possuiInverso(a, n))
            throw new IllegalArgumentException(
                    a + " não tem inverso módulo " + n + ", porque mdc(" + a + ", " + n + ") = " + mdc(a, n));

        // a mesma tabela do Euclides comum, carregando uma coluna a mais: o x
        int restoAnterior = n, resto = a;
        int xAnterior = 0, x = 1;

        while (resto != 0) {
            int quociente = restoAnterior / resto;

            int novoResto = restoAnterior - quociente * resto;
            restoAnterior = resto;
            resto = novoResto;

            int novoX = xAnterior - quociente * x;
            xAnterior = x;
            x = novoX;
        }

        // quando o resto chega em 0, o x da linha anterior é o inverso (pode vir negativo)
        return Math.floorMod(xAnterior, n);
    }
}