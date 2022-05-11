import java.lang.reflect.Array;
import java.util.Arrays;



import java.util.Arrays;

public class IndexServiceImpl implements IndexService{

    private final int CHUNK = 10;
    private final int INITIAL_DIM = 1;
    private int[] elements = new int[INITIAL_DIM];
    private int dim = 0;

    @Override
    public void initialize(int[] keys) {
        if(dim != 0) {
            elements = new int[INITIAL_DIM];
            dim = 0;
        }

        if(keys == null)
            throw new IllegalArgumentException("Cannot take null");

        for (int key : keys)
            insert(key);

        dim = keys.length;
    }

    @Override
    public boolean search(int key) {
        return indexOf(elements, 0, dim - 1, key) != -1;
    }

    static private int indexOf(int[] array, int izq, int der, int element){
        if(izq > der)
            return -1;

        int mid = (der + izq) / 2;

        if(element == array[mid])
            return mid;

        if(array[mid] > element)
            return indexOf(array, izq, mid - 1, element);

        return indexOf(array, mid + 1, der, element);

    }

    static private int closestPosition(int[] array, int izq, int der, int element){
        if(izq > der)
            return izq;

        int mid = (der + izq) / 2;

        if(element == array[mid])
            return mid;

        if(array[mid] > element)
            return closestPosition(array, izq, mid - 1, element);

        return closestPosition(array, mid + 1, der, element);
    }

    @Override
    public void insert(int key) {
        if(elements.length == dim)
            elements = Arrays.copyOf(elements, elements.length + CHUNK);

        int pos = closestPosition(elements, 0, dim - 1, key);

        for(int i = dim - 1; i >= pos; i--)
            elements[i+1] = elements[i];

        elements[pos] = key;
        dim++;
    }

    @Override
    public void delete(int key) {
        int pos;
        if(dim == 0 || (pos = indexOf(elements, 0, dim - 1, key)) == -1)
            return;

        for(int i = pos; i < dim - 1; i++){
            elements[pos] = elements[pos + 1];
        }

        dim--;

        if(dim % CHUNK == 0)
            elements = Arrays.copyOf(elements, dim);
    }

    @Override
    public int occurrences(int key) {
        int posFound = indexOf(elements, 0, dim - 1, key);
        return (posFound != -1) ? countElements(posFound) : 0;
    }

    private int countElements(int pos){
        int count = 1;
        for(int i = 1; pos - i >= 0 && elements[pos - i] == elements[pos]; i++)
            count++;
        for(int i = 1; pos + i < dim && elements[pos + i] == elements[pos]; i++)
            count++;
        return count;
    }

    //Ej2_______________________________________________________________________

    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded){
        leftKey += (leftIncluded) ? 0 : 1 ;
        rightKey += (rightIncluded) ? 0 : 1 ;

        int[] output = new int[INITIAL_DIM];
        int pos = 0;
        for(int i = closestPosition(elements, 0, dim - 1, leftKey); i < dim && elements[i] <= rightKey; i++, pos++){
            if(pos == output.length)
                output = Arrays.copyOf(output, output.length + CHUNK);
            output[pos] = elements[i];
        }
        output = Arrays.copyOf(output, pos + 1);
        return output;
    }

    public void sortedPrint(){
        System.out.println(elements);
    }

    public int getMax(){
        return elements[0];
    }

    public int getMin(){
        return elements[dim - 1];
    }

}