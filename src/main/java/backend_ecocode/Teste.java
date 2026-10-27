package backend_ecocode;

import backend_ecocode.Domain.Usuario;
import backend_ecocode.Service.GerenciarUsuariosService;
import backend_ecocode.Util.CifraCesar;

public class Teste {

    static GerenciarUsuariosService gerenciarUsuariosService = new GerenciarUsuariosService();
    static CifraCesar cifraCesar = new CifraCesar();

    static void main() {
//        String senha = "Rogério Carlos Pedro V3";
//
//        System.out.println(cifraCesar.cifraCesar(senha));
//
//        String senhaCriptografada = cifraCesar.cifraCesar(senha);
//
//        System.out.println(cifraCesar.descriptografarCifra(senhaCriptografada));
//
//        List<String> tentativas = cifraCesar.descriptografarTodasChaves(senhaCriptografada);
//
//        for (int chave = 0; chave < tentativas.size(); chave++) {
//            System.out.println("Chave " + chave + ": " + tentativas.get(chave));
//        }

        Usuario user = gerenciarUsuariosService.buscar("065.314.921-20");
        System.out.println(user.getNomeCompleto());
        System.out.println(user.getDataNascimento());

        gerenciarUsuariosService.editar("06531492120", user.getNomeCompleto(), user.getEmail(), "gilvan06");
    }
}
