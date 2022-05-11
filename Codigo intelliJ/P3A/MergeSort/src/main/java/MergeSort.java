import java.util.Arrays;

public class MergeSort {
    public  static void mergeSort(int[] array){
        mergeSort(array,0,array.length-1);
    }

    public static void mergeSort(int[] array, int left, int right)
// Teorema maestro: 2 T(N/2) + 2N^1   ---> a=2 b=2 c=2 d=1
// a = b^d ---> O(N^d * log(N)) = O(N*log(N))
// Complejidad espacial = O(log(N) + N) = O(N)
    {
        if (left<right) {    // O(1)

            // Find the middle poin
            int mid = left + (right - left) / 2;  // O(1)

            // Sort first and second halves
            mergeSort(array, left, mid);           // T(N/2)
            mergeSort(array, mid + 1, right);  // T(N/2)

            // Merge the sorted halves
            merge(array, left, mid, right);        // O(2N)

        }

    }

    private static void merge(int[] array, int left,int mid, int right ){  // O(2N) = O(N)   con C=2

        int[] leftArray = Arrays.copyOfRange(array,left,mid+1);            // O(N/2)
        int[] rightArray = Arrays.copyOfRange(array,mid+1,right+1);   // O(N/2)

        int leftIdx = 0;
        int rightIdx = 0;
        int copyIdx = left;

        while(leftIdx < leftArray.length && rightIdx < rightArray.length){    // O(N)
            if(leftArray[leftIdx] <= rightArray[rightIdx])
                array[copyIdx++] = leftArray[leftIdx++];
            else
                array[copyIdx++] = rightArray[rightIdx++];
        }

        while(leftIdx<leftArray.length){
            array[copyIdx++] = leftArray[leftIdx++];

        }

        while(rightIdx < rightArray.length) {
            array[copyIdx++] = rightArray[rightIdx++];
        }
    }

}
