package Util;

import Domain.TipoResiduo;

public class ValidacoesResiduos {
    public void validarResiduo(TipoResiduo tipo_residuo, int quantidade, String codigo){

        if (tipo_residuo == null)
            throw new IllegalArgumentException("O tipo do resíduo é obrigatório");

        if (quantidade <= 0)
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");

        if (codigo == null || !codigo.matches("\\d{1,9}"))
            throw new IllegalStateException("Erro interno ao gerar o código do resíduo");
    }
}
