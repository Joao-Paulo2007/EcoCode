package backend_ecocode.Util;

import java.util.regex.Pattern;

public class ValidarEmail {
    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$");

    public static boolean ehValido(String email){
        return EMAIL.matcher(email).matches();
    }
}