package backend_ecocode.Repository;

import backend_ecocode.Domain.RepositoryUsuarioInterface;
import backend_ecocode.Domain.Usuario;
import backend_ecocode.Persistency.Files.PersistenciaArquivo;
import backend_ecocode.Persistency.Persistencia;
import backend_ecocode.Util.Constantes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
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
    public synchronized void cadastrar(Usuario usuarioCadastrado) {
        usuarios.put(usuarioCadastrado.getCpf(), usuarioCadastrado);
        gravar();
    }

    @Override
    public synchronized void editar(Usuario usuarioEditado) {
        usuarios.put(usuarioEditado.getCpf(), usuarioEditado);
        gravar();
    }

    @Override
    public Usuario buscar(String cpf) {
        return usuarios.get(cpf);
    }

    @Override
    public synchronized List<Usuario> listar() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public synchronized void remover(String cpf) {
        usuarios.remove(cpf);
        gravar();
    }

    @Override
    public Usuario buscarEmail(String email) {
        return usuarios.values().stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    private void gravar(){
        persistencia.salvar(listar());
    }
}
