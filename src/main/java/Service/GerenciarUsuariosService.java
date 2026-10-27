package Service;

import Domain.CargoUsuario;
import Domain.ServiceUsuarioInterface;
import Domain.Usuario;
import Exceptions.AcessoNegadoException;
import Repository.GerenciarUsuariosRepository;
import Util.CifraCesar;
import Util.ValidadorCpf;
import Util.ValidarEmail;

import java.util.List;

public class GerenciarUsuariosService implements ServiceUsuarioInterface {
    GerenciarUsuariosRepository gerenciarUsuariosRepository = new GerenciarUsuariosRepository();
    CifraCesar cifraCesar = new CifraCesar();

    @Override
    public Usuario cadastrar(CargoUsuario cargoUsuarioCadastrando, String cpf, String nomeCompleto, String email, String senha, CargoUsuario cargoUsuario, String dataNascimento) {

        if(cargoUsuarioCadastrando != CargoUsuario.GERENTE){
            throw new AcessoNegadoException(
                    "Apenas Gerentes Podem Realizar o Cadastro de Usuario"
            );
        }

        if (gerenciarUsuariosRepository.buscar(ValidadorCpf.limpar(cpf)) != null) {
            throw new IllegalArgumentException("Já existe um usuário com esse CPF.");
        }

        Usuario novoUsuario = new Usuario(
                nomeCompleto, cpf, email, cifraCesar.cifraCesar(senha), cargoUsuario, dataNascimento
        );

        gerenciarUsuariosRepository.cadastrar(novoUsuario);
        return novoUsuario;
    }

    @Override
    public Usuario editar(String cpf, String nomeEditado, String emailEditado, String senhaEditada) {

        Usuario usuarioEditado = buscar(ValidadorCpf.limpar(cpf));

        if(!ValidarEmail.ehValido(emailEditado)){
            throw new IllegalArgumentException("E-mail inválido");
        }

        usuarioEditado.setNomeCompleto(nomeEditado);
        usuarioEditado.setEmail(emailEditado);
        usuarioEditado.setSenhaCriptografada(cifraCesar.cifraCesar(senhaEditada));

        gerenciarUsuariosRepository.editar(usuarioEditado);

        return usuarioEditado;
    }

    @Override
    public Usuario buscar(String cpf) {
        Usuario usuario = gerenciarUsuariosRepository.buscar(ValidadorCpf.limpar(cpf));
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return usuario;
    }

    @Override
    public List<Usuario> listar() {
        return gerenciarUsuariosRepository.listar();
    }

    @Override
    public void remover(CargoUsuario cargoUsuario, String cpf) {
        if(cargoUsuario != CargoUsuario.GERENTE){
            throw new AcessoNegadoException(
                    "Apenas Gerentes Podem Realizar o Cadastro de Usuario"
            );
        }

        // a busca tem validação de encontrar ou não a pessoa
        Usuario usuario = buscar(cpf);
        gerenciarUsuariosRepository.remover(usuario.getCpf());
    }
}
