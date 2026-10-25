package Repository;

import Domain.RepositoryResiduosInterface;
import Domain.Residuo;
import Persistency.Files.PersistenciaArquivo;
import Persistency.Persistencia;
import Util.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GerenciarResiduosRepository implements RepositoryResiduosInterface {

    private Constantes constante = new Constantes();
    private final Persistencia<Residuo> persistencia =
            new PersistenciaArquivo<>(Constantes.RESIDUOS_PATH, Residuo.class);

    private final Map<String, Residuo> residuos = new TreeMap<>();

    public GerenciarResiduosRepository() {
        for (Residuo residuo : persistencia.carregar()) {
            residuos.put(residuo.getCodigo(), residuo);
        }
    }

    @Override
    public void salvar(Residuo residuo) {
        residuos.put(residuo.getCodigo(), residuo);
        gravar();
    }

    @Override
    public void editar(Residuo residuoEditado) {
        residuos.put(residuoEditado.getCodigo(), residuoEditado);
        gravar();
    }

    @Override
    public void excluir(String codigoResiduo) {
        residuos.remove(codigoResiduo);
        gravar();
    }

    @Override
    public List<Residuo> listar() {
        return new ArrayList<>(residuos.values());
    }

    @Override
    public Residuo buscar(String codigoResiduo) {
        return residuos.get(codigoResiduo);
    }

    // toda mudança no Map é copiada para o arquivo
    private void gravar() {
        persistencia.salvar(listar());
    }
}
