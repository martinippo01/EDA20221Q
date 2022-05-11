import java.util.NoSuchElementException;

public class BoundedQueue<T> {

    private T[] bq;
    private int bound, last, first;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public BoundedQueue(int bound){
        if(bound <= 0){
            throw new RuntimeException("Bound must be a positive number");
        }
        this.bound=bound;
        bq = (T[]) new Object[bound];
    }

    public void queue(T elem){
        if(isFull())
            throw new RuntimeException("No space for element to queue");

        if(size == 0){
            first = last = 0;
        }else{
            last = getNext(last);
            if(last == first){
                return;
            }
        }
        bq[last] = elem;
        ++size;
    }

    public T dequeue(){
        if(size == 0){
            throw new RuntimeException("No element to deque");
        }
        T toReturn = bq[first];
        first = getNext(first);
        --size;
        return toReturn;
    }

    private int getNext(int idx){
        if(idx == bound-1){
            return 0;
        }
        return idx+1;
    }

    public T peek(){
        if(size == 0){
            throw new NoSuchElementException("Empty Queue");
        }
        return bq[first];
    }

    public void dump(){
        first=last=size=0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public boolean isFull(){
        return size == bound;
    }

}