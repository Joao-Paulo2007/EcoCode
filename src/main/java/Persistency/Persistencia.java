package Persistency;

import java.util.List;

public interface Persistencia<T> {
    List<T> carregar();
    void salvar(List<T> itens);
}