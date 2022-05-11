import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class MergeSortTest {

    @Test
    public void unsortedTest(){

        int[] unsorted = new int[]{2, 3, 4, 5, 6 , 3, 1, 0, 4};
        int[] sorted = Arrays.copyOf(unsorted, unsorted.length);

        MergeSort.mergeSort(sorted);

        Assertions.assertArrayEquals(sorted, new int[]{0,1,2,3,3,4,4,5,6});
    }

    @Test
    public void equalLengthTest(){
        int[] array1 = new int[]{2,3,1,4,5};
        int[] array2 = Arrays.copyOf(array1, array1.length);

        MergeSort.mergeSort(array2);

        Assertions.assertEquals(array1.length, array2.length);
    }

}
