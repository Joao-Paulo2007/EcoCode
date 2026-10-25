package Domain;

import java.util.List;

public interface RepositoryResiduosInterface {
    public void salvar(Residuo residuo);
    public void editar(Residuo residuoEditado);
    public void excluir(String codigoResiduo);
    public List<Residuo> listar();
    public Residuo buscar(String codigoResiduo);
}
