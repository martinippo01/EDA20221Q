import jdk.jfr.Threshold;

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
    private Slot<K,V>[] Lookup= (Slot<K,V>[]) new Slot[initialLookupSize];


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

        V current = find(key);
        if(current == null){                                        // Slot vacio
            Lookup[ hash( key) ] = new Slot<K, V>(key, data);
            usedKeys++;
            loadFactor = usedKeys / (double)Lookup.length;
            if(loadFactor > Threshold)
                rehash();
        }else{                                                      // Hay algo en el slot
            if(Lookup[hash(key)].getKey() == key )                  // Si tienen la misma key hago update
                Lookup[hash(key)].setValue(data);
            else                                                    // Si no, son dos key distintas => colision
                throw new RuntimeException("Colision en Hash");
        }
    }


    private void rehash(){
        int prevLen = Lookup.length;
        Lookup = Arrays.copyOf(Lookup, 2 * Lookup.length);
        for(int i = 0; i < prevLen; i++){
            // Si en i habia un null  no hay nada para rehashear
            if(Lookup[i] != null){
                K originalKey = Lookup[i].getKey();
                V originalValue = Lookup[i].getValue();
                // Si la clave original hashea a otro lado, se debe reubicar
                if (hash(originalKey) != i) {
                    Lookup[hash(originalKey)] = new Slot<>(originalKey, originalValue);     // Nueva posicion
                    Lookup[i] = null;                                                       // Elimino la posicion anterior
                }
            }
        }
        // Se actualiza el nuevo loadfactor
        loadFactor = usedKeys / (double) Lookup.length;
    }


    // find or get
    public V find(K key) {
        if (key == null)
            return null;

        Slot<K, V> entry = Lookup[hash(key)];
        if (entry == null)
            return null;

        return entry.value;
    }

    public boolean remove(K key) {
        if (key == null)
            return false;

        // lo encontre?
        if (Lookup[ hash( key) ] == null)
            return false;

        Lookup[ hash( key) ] = null;
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

    public static String parseTitle(String line){
        char[] chars = line.toCharArray();
        StringBuilder output = new StringBuilder();
        for(int i = 0; chars[i] != '#'; i++){
            output.append(chars[i]);
        }
        return output.toString();
    }


    public static void main2(String[] args) throws IOException {

        //_____________________________Metodo 1_______________________________________________
        ClosedHashing<String, String> myHash= new ClosedHashing<>(f-> f.codePointAt(0));

        String fileName= "amazon-categories30.txt";
        InputStream is = ClosedHashing.class.getClassLoader().getResourceAsStream(fileName);
        Reader in = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(in);

        String line;
        int colisiones = 0; // colisiones
        while ((line = br.readLine()) != null) {
            String title = parseTitle(line);
            try{
                myHash.insertOrUpdate(title, line);
            }catch (RuntimeException e) {
                colisiones++;
            }
        }
        System.out.println("Cantidad de colisones = " + colisiones);
    }

//Check si esta bien!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static void main(String[] args) throws IOException {
        //_____________________________Metodo 2_______________________________________________
        ClosedHashing<String, String> myHash2= new ClosedHashing<>(f-> {
            int total=0;
            for(int i=0; i< f.length(); i++){
                total+=f.codePointAt(i);
            }
            return total;
        });
        String fileName= "amazon-categories30.txt";
        InputStream is2 = ClosedHashing.class.getClassLoader().getResourceAsStream(fileName);
        Reader in2 = new InputStreamReader(is2);

        BufferedReader br2 = new BufferedReader(in2);

        String line2;
        int colisiones2 = 0; // colisiones
        while ((line2 = br2.readLine()) != null) {
            String title = parseTitle(line2);
            try{
                myHash2.insertOrUpdate(title, line2);
            }catch (RuntimeException e) {
                colisiones2++;
            }
        }
        System.out.println("Cantidad de colisones = " + colisiones2);
    }


}

