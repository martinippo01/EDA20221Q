import java.util.Iterator;

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements SortedListService<T>{

    private Node root;
    private Node last;
    private int size = 0;

    // iterativa
//	@Override
    public boolean insert1(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        Node prev = null;
        Node current= root;

        while( current != null && current.data.compareTo(data) <0    ) {
            // avanzo
            prev= current;
            current= current.next;
        }

        // repetido?
        if ( current!= null && current.data.compareTo(data) ==0  ) {
            System.err.printf("Insertion failed %s%n", data);
            return false;
        }


        // insercion segura
        Node aux= new Node(data, current);

        // como engancho??? cambia el root???
        if (current == root)
            // cambie el primero
            root= aux;
        else  // nodo interno
            prev.next= aux;

        return true;
    }



    // recursiva desde afuera
//	@Override
    public boolean insert2(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        boolean[] rta= new boolean[1];
        root =insertRec( data, root,  rta);

        return rta[0];
    }


    private Node insertRec(T data, Node current, boolean[] rta ) {
        // repetido?
        if ( current!= null && current.data.compareTo(data) ==0  ) {
            System.err.printf("Insertion failed %s%n", data);
            rta[0]= false;
            return current;
        }

        if( current != null && current.data.compareTo(data) <0    ) {
            // avanzo
            current.next   = insertRec(data, current.next, rta);
            return current;
        }


        // estoy en parado en el lugar a insertar
        rta[0]= true;
        return new Node(data, current);

    }

    // delega en Node
    @Override
    public boolean insert(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        if (root == null) {
            root = new Node(data, null);
            last = root;
            size++;
            return true;
        }

        boolean[] rta= new boolean[1];
        root = root.insert( data,  rta);

        if(rta[0]){
            size++;
        }

        return rta[0];
    }


    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }


    // delete resuelto todo en la clase SortedLinkedList, iterativo
    @Override
    public boolean remove(T data) {
        if(data == null)
            return false;

        Node prev = null;
        Node current = root;

        while(current != null && current.data.compareTo(data) < 0){
            prev = current;
            current = current.next;
        }

        if(current != null && current.data.compareTo(data) == 0){// Lo encontre
            if(prev != null)        //El anterior no es null -> No es el primero
                prev.next = current.next;
            else                    //El anterior es null -> Es el primero
                root = current.next;

            if(current.next == null)//El que sigue null -> Es el ultimo
                last = prev;

            return true;
        }
        // No lo encontre
        return false;
    }



    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public void dump() {
        Node current = root;

        while (current!=null ) {
            // avanzo
            System.out.println(current.data);
            current= current.next;
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other == null || !  (other instanceof SortedLinkedList) )
            return false;

        @SuppressWarnings("unchecked")
        SortedLinkedList<T> auxi = (SortedLinkedList<T>) other;

        Node current = root;
        Node currentOther= auxi.root;
        while (current!=null && currentOther != null ) {
            if (current.data.compareTo(currentOther.data) != 0)
                return false;

            // por ahora si, avanzo ambas
            current= current.next;
            currentOther= currentOther.next;
        }

        return current == null && currentOther == null;

    }

    // -1 si no lo encontro
    protected int getPos(T data) {
        Node current = root;
        int pos= 0;

        while (current!=null ) {
            if (current.data.compareTo(data) == 0)
                return pos;

            // avanzo
            current= current.next;
            pos++;
        }
        return -1;
    }

    @Override
    public T getMin() {
        return root.data;
    }


    @Override
    public T getMax() {
        return last.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node pre_prev = null;
            Node prev = null;
            Node current = root;
            boolean removed = true;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                removed = false;
                Node aux = current;
                pre_prev = (prev != null) ? prev : null ;
                prev = current;
                current = current.next;
                return aux.data;
            }

            @Override
            public void remove() {
                if(removed)
                    throw new IllegalStateException("Cannot remove without invoking next, nor invoking twice between next calls");
                else
                    removed = true;

                if(prev != null){
                    if(prev == root) {
                        root = current;
                    }
                    else if(current != null) {
                        pre_prev.next = current;
                    }else {
                        pre_prev.next = null;
                        last = pre_prev;
                    }
                }

            }
        };
    }


    private final class Node {
        private final T data;
        private Node next;

        private Node(T data) {
            this.data= data;
        }

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        private Node insert(T data, boolean[] rta) {

            if ( this.data.compareTo(data) == 0 ) {
                System.err.printf("Insertion failed %s%n", data);
                rta[0] = false;
                return this;
            }

            if( this.data.compareTo(data) < 0 ) {
                // soy el ultimo?
                if (next == null) {
                    rta[0]= true;
                    next = new Node(data, null);
                    last = next;
                    return this;
                }


                // avanzo
                next = next.insert(data, rta);
                return this;
            }


            // estoy en parado en el lugar a insertar
            rta[0]= true;
            Node aux = new Node(data, this);
            if(next == null){
                last = aux;
            }
            return aux;
        }

    }



    public static void mainl(String[] args) {
        SortedLinkedList<Integer> l = new SortedLinkedList<>();
        l.insert(30);
        l.insert(80);
        l.insert(40);
        l.insert(40);
        l.insert(25);
        l.insert(90);
        l.insert(12);

        for(Integer num : l){
            System.out.println(num);
        }

        System.out.println("Maximo: " + l.getMax());
        System.out.println("Minimo: " + l.getMin());
        System.out.println("Size: " + l.size);
    }

    public static void main(String[] args) {
        SortedLinkedList<Integer> lista = new SortedLinkedList<>();
        for(int i = 0; i <= 10; i++){
            lista.insert(i);
        }
        lista.dump();
        System.out.println("_______________________");
        Iterator<Integer> iter = lista.iterator();
        int data;
        while(iter.hasNext()) {
            data = iter.next();
            if ((data) % 2 == 0)
                iter.remove();
        }

        lista.dump();

    }




}