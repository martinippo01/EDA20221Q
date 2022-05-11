import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class ClosedHashing<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize = 10;
    private int usedKeys = 0;
    private  double loadFactor = 0;
    private final double Threshold = 0.75;


    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private Slot<K,V>[] Lookup = (Slot<K,V>[]) new Slot[initialLookupSize];


    private Function<? super K, Integer> prehash;

    public ClosedHashing( Function<? super K, Integer> mappingFn) {
        if (mappingFn == null)
            throw new RuntimeException("fn not provided");
        prehash = mappingFn;
    }

    // ajuste al tamaño de la tabla
    private int hash(K key) {
        if (key == null)
            throw new IllegalArgumentException("key cannot be null");
        return prehash.apply(key) % Lookup.length;
    }



    public void insertOrUpdate(K key, V data) {
        if (key == null || data == null) {
            String msg= String.format("inserting or updating (%s,%s). ", key, data);
            if (key==null)
                msg+= "Key cannot be null. ";
            if (data==null)
                msg+= "Data cannot be null.";
            throw new IllegalArgumentException(msg);
        }

        int current = findFirstAvailablePos(key);

        if(Lookup[current] != null && Lookup[current].active){
            Lookup[current].setValue(data);
        } else{
            Lookup[current] = new Slot<>(key, data);
            usedKeys++;
            loadFactor = usedKeys / (double) Lookup.length;
            if(loadFactor > Threshold){
                rehash();
            }
        }

    }


    private void rehash(){
        int prevLen = Lookup.length;
        Slot<K,V>[] aux = Lookup;
        Lookup = Arrays.copyOf(Lookup, 2 * Lookup.length);

        for(int i = 0; i < prevLen; i++){
            if(aux[i] != null)
                insertOrUpdate(aux[i].getKey(), aux[i].getValue());
        }
    }


    // find or get
    private int findFirstAvailablePos(K key) {
        int pos;
        int available = -1;
        boolean found = false;
        for(pos = hash(key); Lookup[pos] != null && !found; pos = (pos + 1) % Lookup.length){
            if(Lookup[pos].getKey().equals(key) || ((hash(key) == pos) && available != -1)) {
                found = true;
                pos = (pos - 1) %Lookup.length;
            }
            else
                if(available == -1 && !(Lookup[pos].active))
                    available = pos;
        }
        // Si tengo una baja logica antes de la primera fisica -> Available
        // Si no, -> pos
        return (available != -1 && !found) ? available : pos;
    }

    // find or get
    public V find(K key) {
        int pos;
        boolean found = false;
        for(pos = hash(key); Lookup[pos] != null && !found; pos = (pos + 1) % Lookup.length){
            if(Lookup[pos].getKey().equals(key)){
                found = true;
                pos = (pos - 1) % Lookup.length;
            }
        }
        return (Lookup[pos] != null && Lookup[pos].active) ? Lookup[pos].getValue() : null;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        // lo encontre?
        int pos = findFirstAvailablePos(key);

        // Si es una baja fisica o logica no lo encontre
        if(Lookup[pos] == null || !(Lookup[pos].active))
            return false;

        if(Lookup[(pos + 1) % Lookup.length] == null)
            Lookup[pos] = null;
        else
            Lookup[pos].deactivate();

        usedKeys--;
        loadFactor = usedKeys / (double) Lookup.length;
        return true;
    }


    public void dump()  {
        for(int rec= 0; rec < Lookup.length; rec++) {
            if (Lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.println(String.format("slot %d contains %s",rec, Lookup[rec]));
        }
    }


    public int size() {
        return usedKeys;
    }



    static private final class Slot<K, V>	{
        private final K key;
        private V value;
        private boolean active;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
            active = true;
        }

        private void deactivate(){
            active = false;
        }

        private void activate(){
            active = true;
        }

        private boolean getStatus(){
            return active;
        }

        private void setValue(V value){
            this.value = value;
        }

        private K getKey(){
            return key;
        }

        private V getValue(){
            return value;
        }

        public String toString() {
            return String.format("(key=%s, value=%s)", key, value );
        }
    }

/*
    public static void main(String[] args) {
        ClosedHashing<Integer, String> myHash= new ClosedHashing<>(f->f);
        myHash.insertOrUpdate(55, "Ana");
        myHash.insertOrUpdate(44, "Juan");
        myHash.insertOrUpdate(18, "Paula");
        myHash.insertOrUpdate(19, "Lucas");
        myHash.insertOrUpdate(21, "Sol");
        System.out.println("______________________________________");
        myHash.dump();
        // Hacemos algo que requiera update
        myHash.insertOrUpdate(21, "update");
        System.out.println("______________________________________");
        myHash.dump();
        // Hacemos algo que requiera rehash
        myHash.insertOrUpdate(30, "Quilombero1");
        myHash.insertOrUpdate(33, "Quilombero2");
        myHash.insertOrUpdate(37, "Quilombero3");
        System.out.println("______________________________________");
        System.out.println("Ahora nos pasamos del load factor:");
        myHash.dump();

    }
    */

}

