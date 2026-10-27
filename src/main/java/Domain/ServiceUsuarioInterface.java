package Domain;

import java.util.List;

public interface ServiceUsuarioInterface {
    public Usuario cadastrar(CargoUsuario cargoUsuarioCadastrando, String cpf, String nomeCompleto, String email, String senha, CargoUsuario cargoUsuario, String dataNascimento);
    public Usuario editar(String cpf, String nomeEditado, String emailEditado, String senhaEditada);
    public Usuario buscar(String cpf);
    public List<Usuario> listar();
    public void remover(CargoUsuario cargoUsuario, String cpf);
}
