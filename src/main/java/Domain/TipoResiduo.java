package Domain;

public enum TipoResiduo {
    COMPUTADORES((byte) 0, "Computadores e Notebooks"),
    CELULARES((byte) 1, "Celulares e Tablest"),
    CABOS((byte) 2, "Cabos e Periféricos"),
    BATERIAS((byte) 3, "Pilhas e Baterias"),
    OUTROS((byte) 4, "Outros Eletrônicos");

    private final byte resto;
    private final String descricao;

    TipoResiduo(byte resto, String descricao) {
        this.descricao = descricao;
        this.resto = resto;
    }

    public String getDescricao() {
        return descricao;
    }

    public Byte getResto() {
    return resto;
    }
}
