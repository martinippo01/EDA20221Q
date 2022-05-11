import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class tests {
    @Test
    public void check() {
        IndexParametricService<Integer, Integer> h = new ClosedHashing<>(n -> 1);

        for (int i = 0; i < 10000; i++) {
            h.insertOrUpdate(i,i);
        }

        for (int i = 0; i < 500; i++) {
            int rand = getRandom(10000);
            Assertions.assertEquals(rand, h.find(rand));
        }

        ArrayList<Integer> removed = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            int rand = getRandom(10000);
            if(removed.contains(rand))
                System.out.println("repetido");
            else {
                removed.add(rand);
                Assertions.assertTrue(h.remove(rand));
            }
        }

        for (Integer i : removed) {
            Assertions.assertNull(h.find(i));
        }
    }

    public static int getRandom(int upperBound) {
        Random rand = new Random(); //instance of random class
        //generate random values from 0-upperBound
        return rand.nextInt(upperBound);
    }
}
