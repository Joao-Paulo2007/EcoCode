package Util;

import Domain.TipoResiduo;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class GerarCodigoResiduo {

    // estes dois campos precisam vir ANTES do "ultimoCodigo", porque o carregarArquivo() usa os dois
    private final Path caminho = Path.of(Constantes.CODIGO_PATH);
    private final Gson gson = new Gson();

    // último código gerado, de qualquer tipo, lido do arquivo (0 se ainda não existe)
    private int ultimoCodigo = carregarArquivo();

    // próximo código = menor número depois do último cujo (código mod 5) é o resto do tipo
    public String gerarCodigo(TipoResiduo tipoResiduo) {

        if (tipoResiduo == null)
            throw new IllegalArgumentException("O tipo do resíduo é obrigatório");

        int candidato = ultimoCodigo + 1;

        // quantas casas faltam, a partir do candidato, até chegar no resto do tipo (0 a 4)
        int distancia = Math.floorMod(tipoResiduo.getResto() - candidato, 5);

        int proximo = candidato + distancia;

        ultimoCodigo = proximo;
        salvarArquivo();

        return String.format("%06d", proximo);
    }

    private int carregarArquivo() {
        if (!Files.exists(caminho)) {
            return 0;
        }

        try (Reader leitura = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            Integer lido = gson.fromJson(leitura, Integer.class);
            return lido != null ? lido : 0;
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível ler o arquivo " + caminho, e);
        }
    }

    private void salvarArquivo() {
        try {
            Path pasta = caminho.getParent();
            if (pasta != null) {
                Files.createDirectories(pasta);
            }

            try (Writer escrita = Files.newBufferedWriter(caminho, StandardCharsets.UTF_8)) {
                gson.toJson(ultimoCodigo, escrita);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível salvar o arquivo " + caminho, e);
        }
    }
}