package backend_ecocode.Domain;

import backend_ecocode.Util.ValidacoesResiduos;

public class Residuo {

    private String codigo;
    private TipoResiduo tipo_residuo;
    private int quantidade;
    private String descricao;

    private static final ValidacoesResiduos validar = new ValidacoesResiduos();

    public Residuo(String codigo, TipoResiduo tipo_residuo, int quantidade, String descricao){

        validar.validarResiduo(tipo_residuo, quantidade, codigo);

        this.codigo = codigo;
        this.tipo_residuo = tipo_residuo;
        this.quantidade = quantidade;
        this.descricao = (descricao == null) ? "" : descricao.trim();
    }

    public String getCodigo(){
        return codigo;
    }

    public String getCategoriaResiduo(){
        return tipo_residuo.getDescricao();
    }

    public TipoResiduo getResiduo(){
        return tipo_residuo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
