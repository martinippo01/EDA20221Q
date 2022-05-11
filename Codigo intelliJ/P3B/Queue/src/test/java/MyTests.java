import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class MyTests {
    @Test
    public void checkFunctionality() {
        BoundedQueue<Integer> q = new BoundedQueue<>(5);

        Assertions.assertTrue(q.isEmpty());
        Assertions.assertFalse(q.isFull());

        Assertions.assertThrows(RuntimeException.class, () -> q.dequeue());
        q.queue(1);
        q.queue(2);
        q.queue(3);
        q.queue(4);
        q.queue(5);
        Assertions.assertThrows(RuntimeException.class, () -> q.queue(1));

        Assertions.assertEquals(1, q.dequeue());
        Assertions.assertEquals(2, q.dequeue());
        Assertions.assertEquals(3, q.dequeue());
        Assertions.assertEquals(4, q.dequeue());
        Assertions.assertEquals(5, q.dequeue());
        Assertions.assertTrue(q.isEmpty());

        q.queue(1);
        q.queue(2);
        q.queue(3);
        q.queue(4);
        q.queue(5);
        q.dequeue();
        q.queue(6);
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        Assertions.assertEquals(6, q.dequeue());
    }
}
