public interface IndexService {

    // elements serán los valores del índice, los anteriores se descartan.
    // lanza excepction si elements is null y deja los valores anteriores.
    void initialize(int [] elements);
    // busca una key en el índice, O(log2 N)
    boolean search(int key);

    // inserta el key en pos correcta. Crece automáticamente de a chunks!!
    void insert(int key);

    // borra el key si lo hay, sino lo ignora.
    // decrece automáticamente de a chunks
    void delete(int key);

    // devuelve la cantidad de apariciones de la clave especificada
    int occurrences(int key);

}
