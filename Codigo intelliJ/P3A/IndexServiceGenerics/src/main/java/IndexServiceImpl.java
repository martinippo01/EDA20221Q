import java.lang.reflect.Array;
import java.util.Arrays;

public class IndexServiceImpl<T extends Comparable<? super T>> implements IndexService<T> {
    private static final int CHUNK = 5;
    private int index = 0;
    private T[] array;
    private final Class<?> of;

    @SuppressWarnings("unchecked")
    public IndexServiceImpl(Class<?> of) {
        this.of = of;
        this.array = (T[]) Array.newInstance(of, CHUNK);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(T[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException();
        }
        array = (T[]) Array.newInstance(of, CHUNK);
        index = 0;
        for (T i : elements) {
            insert(i);
        }
    }

    @Override
    public boolean search(T k) {
        return binarySearch(k) >= 0;
    }

    @Override
    public void insert(T k) {
        if (array.length == index) {
            resize();
        }
        int positionToInsert = getClosestPosition(k);
        System.arraycopy(array, positionToInsert + 1, array, positionToInsert + 2, index - positionToInsert - 1);
        array[positionToInsert + 1] = k;
        index++;
    }

    private void resize() {
        array = Arrays.copyOf(array, index + CHUNK);
    }

    @Override
    public void delete(T k) {
        int kIndex = binarySearch(k);
        if (kIndex >= 0) {
            System.arraycopy(array, kIndex + 1, array, kIndex, index - kIndex);
            index--;
        }
    }

    @Override
    public int occurrences(T k) {
        int occ = 0;
        int occIndex = binarySearch(k);
        if (occIndex >= 0) {
            occ++;
            for (int i = occIndex + 1; i < index && array[i].compareTo(k) == 0; i++) {
                occ++;
            }
            for (int i = occIndex - 1; i >= 0 && array[i].compareTo(k) == 0; i--) {
                occ++;
            }
        }
        return occ;
    }

    @Override
    public int size() {
        return index;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded) {
        int left = getClosestPosition(leftKey);
        if (leftIncluded) {
            while (left >= 0 && array[left].compareTo(leftKey) == 0) {
                left--;
            }
        }
        int right = getClosestPosition(rightKey);
        if (!rightIncluded) {
            while (right >= 0 && array[right].compareTo(rightKey) == 0) {
                right--;
            }
        }
        int arrayLength = right - left;
        T[] rangeArray = (T[]) Array.newInstance(of, arrayLength > 0 ? arrayLength : 0);
        int rangeIndex = 0;
        for (int i = left + 1; i <= right; i++, rangeIndex++) {
            rangeArray[rangeIndex] = array[i];
        }
        return rangeIndex == 0 ? (T[]) Array.newInstance(of, 0) : rangeArray;
    }

    @Override
    public void sortedPrint() {
        for (int i = 0; i < index; i++) {
            System.out.printf("%s ", array[i]);
        }
        System.out.println();
    }

    @Override
    public T getMax() {
        if (index == 0) {
            throw new IllegalStateException();
        }
        return array[index-1];
    }

    @Override
    public T getMin() {
        if (index == 0) {
            throw new IllegalStateException();
        }
        return array[0];
    }

    private int getClosestPosition(T k) {
        int candidate = -1;
        int left = 0;
        int right = index - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (array[middle].compareTo(k) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
                candidate = middle;
            }
        }
        return candidate;
    }

    private int binarySearch(T k) {
        int left = 0;
        int right = index - 1;
        while (left <= right) {
            int middle = (left + right) / 2;
            if (array[middle].compareTo(k) > 0) {
                right = middle - 1;
            } else if (array[middle].compareTo(k) < 0) {
                left = middle + 1;
            } else {
                return middle;
            }
        }
        return -1;
    }


}
