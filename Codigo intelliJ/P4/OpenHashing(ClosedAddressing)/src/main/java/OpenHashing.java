import jdk.jfr.Threshold;
import sun.awt.image.ImageWatched;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Function;

public class OpenHashing<K, V> implements IndexParametricService<K, V> {
    final private int initialLookupSize = 10;
    private int usedKeys = 0;
    private  double loadFactor = 0;
    private final double Threshold = 0.75;


    // estática. No crece. Espacio suficiente...
    @SuppressWarnings({"unchecked"})
    private LinkedList<Slot<K,V>>[] Lookup = (LinkedList<Slot<K,V>>[]) new LinkedList[initialLookupSize];


    private Function<? super K, Integer> prehash;

    public OpenHashing( Function<? super K, Integer> mappingFn) {
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

        V current = find(key);
        if(current == null){                                        // Slot vacio
            (Lookup[ hash(key) ] = new LinkedList<>()).add(new Slot<>(key,data));
            usedKeys++;
            loadFactor = usedKeys / (double)Lookup.length;
            if(loadFactor > Threshold)
                rehash();
        }else{                                                      // Hay algo en el slot
            Lookup[hash(key)].remove(new Slot<>(key, data));        // Borro el elemento
            Lookup[hash(key)].add(new Slot<>(key, data));           // Inserto el nuevo
        }
    }


    private void rehash(){
        int prevLen = Lookup.length;

        LinkedList<Slot<K,V>>[] aux = Lookup;
        Lookup = Arrays.copyOf(Lookup, 2 * Lookup.length);


        

        // Se actualiza el nuevo loadfactor
        loadFactor = usedKeys / (double) Lookup.length;
    }


    // find or get
    public V find(K key) {
        if (key == null)
            return null;

        LinkedList<Slot<K,V>> slots = Lookup[hash(key)];
        if (slots == null)
            return null;

        V value = null;

        for(Slot<K,V> slot : slots){
            if(slot.getKey().equals(key))
                value = slot.getValue();
        }

        return value;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        // lo encontre?
        boolean toReturn = Lookup[hash(key)].remove(new Slot<>(key, null));


        if(Lookup[hash(key)].isEmpty()){
            Lookup[hash(key)] = null;
            usedKeys--;
            loadFactor = usedKeys / (double) Lookup.length;
        }

        return toReturn;
    }


    public void dump()  {
        System.out.println("DUMP - NO IMPLEMENTADO");
        /*for(int rec= 0; rec < Lookup.length; rec++) {
            if (Lookup[rec] == null)
                System.out.println(String.format("slot %d is empty", rec));
            else
                System.out.println(String.format("slot %d contains %s",rec, Lookup[rec]));
        }*/
    }


    public int size() {
        // todavia no esta implementado
        return 0;
    }



    static private final class Slot<K, V>	{
        private final K key;
        private V value;

        private Slot(K theKey, V theValue){
            key= theKey;
            value= theValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Slot<?, ?> slot = (Slot<?, ?>) o;
            return Objects.equals(key, slot.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
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

}
