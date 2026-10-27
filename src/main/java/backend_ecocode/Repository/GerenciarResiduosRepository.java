package backend_ecocode.Repository;

import backend_ecocode.Domain.RepositoryResiduosInterface;
import backend_ecocode.Domain.Residuo;
import backend_ecocode.Persistency.Files.PersistenciaArquivo;
import backend_ecocode.Persistency.Persistencia;
import backend_ecocode.Util.Constantes;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class GerenciarResiduosRepository implements RepositoryResiduosInterface {

    private Constantes constante = new Constantes();
    private final Persistencia<Residuo> persistencia =
            new PersistenciaArquivo<>(Constantes.RESIDUOS_PATH, Residuo.class);

    private final Map<String, Residuo> residuos = new LinkedHashMap<>();

    public GerenciarResiduosRepository() {
        for (Residuo residuo : persistencia.carregar()) {
            residuos.put(residuo.getCodigo(), residuo);
        }
    }

    @Override
    public synchronized void salvar(Residuo residuo) {
        residuos.put(residuo.getCodigo(), residuo);
        gravar();
    }

    @Override
    public synchronized void editar(Residuo residuoEditado) {
        residuos.put(residuoEditado.getCodigo(), residuoEditado);
        gravar();
    }

    @Override
    public synchronized void excluir(String codigoResiduo) {
        residuos.remove(codigoResiduo);
        gravar();
    }

    @Override
    public synchronized List<Residuo> listar() {
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
