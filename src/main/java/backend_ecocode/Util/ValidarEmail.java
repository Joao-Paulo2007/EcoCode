package backend_ecocode.Util;

import backend_ecocode.Service.GerenciarUsuariosService;

import java.util.regex.Pattern;

public class ValidarEmail {
    private static GerenciarUsuariosService gerenciarUsuariosService = new GerenciarUsuariosService();

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$");

    public static boolean ehValido(String email){
        return EMAIL.matcher(email).matches();
    }

    public static boolean emailExiste(String email){
        if(gerenciarUsuariosService.buscarEmail(email) != null) return true;

        return false;
    }
}