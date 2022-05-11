
public class IdxRecord<T1 extends Comparable<T1>, T2> implements Comparable<IdxRecord<T1, T2>>{

    private T1 key;
    private T2 row;

    public IdxRecord(T1 myKey) {
        key= myKey;
    }


    public IdxRecord(T1 myKey, T2 myRow) {
        key= myKey;
        row= myRow;
    }

    public int compareTo(IdxRecord<T1,T2> other) {
        return key.compareTo(other.key);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof IdxRecord)
            return this.compareTo((IdxRecord<T1, T2>) obj) == 0;

        return super.equals(obj);
    }

    public String toString() {
        return String.format("%s %s", key, row);
    }

    public T1 getKey() {
        return key;
    }

    public T2 getRow() {
        return row;
    }


}