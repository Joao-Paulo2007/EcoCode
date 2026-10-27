package Repository;

import Domain.RepositoryUsuarioInterface;
import Domain.Usuario;
import Persistency.Files.PersistenciaArquivo;
import Persistency.Persistencia;
import Util.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GerenciarUsuariosRepository implements RepositoryUsuarioInterface {

    private Constantes constante = new Constantes();
    private final Persistencia<Usuario> persistencia =
            new PersistenciaArquivo<>(Constantes.USUARIOS_PATH, Usuario.class);

    private final Map<String, Usuario> usuarios = new TreeMap<>();

    public GerenciarUsuariosRepository() {
        for (Usuario usuario : persistencia.carregar()) {
            usuarios.put(usuario.getCpf(), usuario);
        }
    }

    @Override
    public void cadastrar(Usuario usuarioCadastrado) {
        usuarios.put(usuarioCadastrado.getCpf(), usuarioCadastrado);
        gravar();
    }

    @Override
    public void editar(Usuario usuarioEditado) {
        usuarios.put(usuarioEditado.getCpf(), usuarioEditado);
        gravar();
    }

    @Override
    public Usuario buscar(String cpf) {
        return usuarios.get(cpf);
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public void remover(String cpf) {
        usuarios.remove(cpf);
        gravar();
    }

    private void gravar(){
        persistencia.salvar(listar());
    }
}
