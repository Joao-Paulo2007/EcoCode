package backend_ecocode.Util;

public class CalcularRestoResiduo {
        public static int calcularResto(String codigo) {

            int numero = Integer.parseInt(codigo);
            return numero % 5;
        }
}