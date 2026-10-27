package backend_ecocode.Domain;

import java.util.List;

public interface RepositoryUsuarioInterface {
    public void cadastrar(Usuario usuarioCadastrado);
    public void editar(Usuario usuarioEditado);
    public Usuario buscar(String cpf);
    public List<Usuario> listar();
    public void remover(String cpf);
}
