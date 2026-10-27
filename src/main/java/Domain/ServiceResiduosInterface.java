package Domain;

import java.util.List;

public interface ServiceResiduosInterface {
    public Residuo salvar(TipoResiduo tipoResiduo, int quantidadeResiduo, String descricao);
    public Residuo editar(String codigoResiduo, int quantidadeResiduo, String descricao);
    public void excluir(String codigoResiduo);
    public List<Residuo> listar();
    public List<Residuo> listarPorTipo(TipoResiduo tipoResiduo);
    public List<Residuo> listarPorCodigo();
    public Residuo buscar(String codigoResiduo);
}
