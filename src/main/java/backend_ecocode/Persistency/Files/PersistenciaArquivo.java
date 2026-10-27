package backend_ecocode.Persistency.Files;

import backend_ecocode.Persistency.Persistencia;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class PersistenciaArquivo<T> implements Persistencia<T> {

    private final Path caminho;
    private final Type tipoLista;

    // Gson adaptado para conseguir salvar o localdate do usuário
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
                @Override
                public void write(JsonWriter saida, LocalDate data) throws IOException {
                    if (data == null) saida.nullValue();
                    else saida.value(data.toString());          // "2006-05-14"
                }

                @Override
                public LocalDate read(JsonReader entrada) throws IOException {
                    if (entrada.peek() == JsonToken.NULL) {
                        entrada.nextNull();
                        return null;
                    }
                    return LocalDate.parse(entrada.nextString());
                }
            })
            .create();;

    public PersistenciaArquivo(String caminho, Class<T> tipo) {
        this.caminho = Path.of(caminho);
        this.tipoLista = TypeToken.getParameterized(List.class, tipo).getType();
    }

    @Override
    public void salvar(List<T> itens) {
        try {
            Path pasta = caminho.getParent();
            if (pasta != null) {
                Files.createDirectories(pasta);
            }

            try (Writer escrita = Files.newBufferedWriter(caminho, StandardCharsets.UTF_8)) {
                gson.toJson(itens, tipoLista, escrita);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível salvar o arquivo " + caminho, e);
        }
    }

    @Override
    public List<T> carregar() {
        if (!Files.exists(caminho)) {
            return new ArrayList<>();
        }

        try (Reader leitura = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            List<T> itens = gson.fromJson(leitura, tipoLista);
            return itens != null ? itens : new ArrayList<>();
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível ler o arquivo " + caminho, e);
        }
    }

    public <T> Map<String, T> listaParaMap(List<T> lista, Function<T, String> chave) {
        Map<String, T> mapa = new TreeMap<>();

        for (T item : lista) {
            mapa.put(chave.apply(item), item);
        }

        return mapa;
    }
}