package backend_ecocode.Service;

import backend_ecocode.Domain.ServiceResiduosInterface;
import backend_ecocode.Domain.Residuo;
import backend_ecocode.Domain.TipoResiduo;
import backend_ecocode.Repository.GerenciarResiduosRepository;
import backend_ecocode.Util.CalcularRestoResiduo;
import backend_ecocode.Util.GerarCodigoResiduo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GerenciarResiduosService implements ServiceResiduosInterface {

    private GerenciarResiduosRepository gerarResiduosRepository = new GerenciarResiduosRepository();
    private final GerarCodigoResiduo gerarCodigoResiduo = new GerarCodigoResiduo();
    private final CalcularRestoResiduo calcularResto = new CalcularRestoResiduo();
    @Override
    public Residuo salvar(TipoResiduo tipoResiduo, int quantidadeResiduo, String descricao) {

        // verifica se foi informado um tipo de residuo
        if (tipoResiduo == null)
            throw new IllegalArgumentException(
                    "O tipo do resíduo é obrigatório"
            );

        // verifica se a quantidade é maior que 0
        if (quantidadeResiduo <= 0)
            throw new IllegalArgumentException("" +
                    "A quantidade deve ser maior que zero"
            );

        String codigoResiduo = gerarCodigoResiduo.gerarCodigo(tipoResiduo);

        // gera um novo código para o residuo cadastrado caso o código gerado já esteja cadastrado
        while (gerarResiduosRepository.buscar(codigoResiduo) != null) {
            codigoResiduo = gerarCodigoResiduo.gerarCodigo(tipoResiduo);
        }

        Residuo residuoNovo = new Residuo(codigoResiduo, tipoResiduo, quantidadeResiduo, descricao);

        gerarResiduosRepository.salvar(residuoNovo);
        return residuoNovo;
    }

    @Override
    public Residuo editar(String codigoResiduo, int quantidadeResiduo, String descricao) {
        Residuo editarResiduo = buscar(codigoResiduo);

        if(quantidadeResiduo <= 0){
            throw new IllegalArgumentException(
                    "A quantidade deve ser maior que zero"
            );
        }

        editarResiduo.setQuantidade(quantidadeResiduo);
        editarResiduo.setDescricao(descricao);

        gerarResiduosRepository.editar(editarResiduo);
        return editarResiduo;
    }

    @Override
    public void excluir(String codigoResiduo) {
        Residuo residuo = buscar(codigoResiduo);
        gerarResiduosRepository.excluir(residuo.getCodigo());
    }

    @Override
    public List<Residuo> listar() {
        return gerarResiduosRepository.listar();
    }

    @Override
    public Residuo buscar(String codigoResiduo) {

        if (codigoResiduo == null || !codigoResiduo.trim().matches("\\d{1,9}"))
            throw new IllegalArgumentException("O código deve conter apenas números");

        String codigo = String.format("%06d", Integer.parseInt(codigoResiduo.trim()));

        Residuo residuo = gerarResiduosRepository.buscar(codigo);

        if (residuo == null)
            throw new IllegalArgumentException("Resíduo não encontrado!");

        return residuo;
    }

    @Override
    public List<Residuo> listarPorTipo(TipoResiduo tipoResiduo) {

        if (tipoResiduo == null)
            throw new IllegalArgumentException("O tipo do resíduo é obrigatório");

        List<Residuo> filtrados = new ArrayList<>();

        for (Residuo residuo : gerarResiduosRepository.listar()) {
            if (residuo.getResiduo() == tipoResiduo) {
                filtrados.add(residuo);
            }
        }

        return filtrados;
    }

    // lista por ordem crescente de código
    @Override
    public List<Residuo> listarPorCodigo() {
        List<Residuo> lista = gerarResiduosRepository.listar();
        lista.sort(Comparator.comparingInt(residuo -> Integer.parseInt(residuo.getCodigo())));
        return lista;
    }

    // Etapa 2: h(k) = k mod 5, calculado a partir do código do resíduo
    public int calcularClasse(Residuo residuo) {
        return calcularResto.calcularResto(residuo.getCodigo());
    }

    // confere se a classe calculada pelo código bate com a categoria do tipo
    public boolean classeConfere(Residuo residuo) {
        return calcularClasse(residuo) == residuo.getResiduo().getResto();
    }

    // pegar a quantidade de residuos cadastrados
    public int quantidadeResiduosCadastrados() {
        return gerarResiduosRepository.listar().size();
    }

    // pegar a quantidade total de residuos (quantidade)
    public int quantidadeTotalResiduos() {
        int total = 0;

        for (Residuo residuo : listar()) {
            total += residuo.getQuantidade();
        }

        return total;
    }

}