import org.junit.jupiter.api.Assertions;

    import org.junit.jupiter.api.*;

    public class TestsAxel {

        @Test
        public void nullTest() {
            IndexService MyIndex = new IndexServiceImpl();
            Assertions.assertThrows(RuntimeException.class, () -> MyIndex.initialize(null));
        }

        @Test
        public void noOccurrencesTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 2, 5, 8});
            Assertions.assertFalse(MyIndex.search(9));
            Assertions.assertEquals(0, MyIndex.occurrences(9));
        }

        @Test
        public void moreThanOneOcurrenceAtStartTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 3, 9, 12, 3});
            Assertions.assertTrue(MyIndex.search(3));
            Assertions.assertEquals(3, MyIndex.occurrences(3));
        }

        @Test
        public void moreThanOneOcurrenceAtEndTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 9, 15, 12, 3});
            Assertions.assertTrue(MyIndex.search(15));
            Assertions.assertEquals(2, MyIndex.occurrences(15));
        }

        @Test
        public void moreThanOneOcurrenceMiddleTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 5, 3, 9, 5, 15, 12, 3, 5});
            Assertions.assertTrue(MyIndex.search(5));
            Assertions.assertEquals(4, MyIndex.occurrences(5));
        }

        @Test
        public void onlyOneOcurrenceAtStartTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            Assertions.assertTrue(MyIndex.search(1));
            Assertions.assertEquals(1, MyIndex.occurrences(1));
        }

        @Test
        public void onlyOneOcurrenceAtEndTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 20, 1, 9, 15, 12, 3});
            Assertions.assertTrue(MyIndex.search(20));
            Assertions.assertEquals(1, MyIndex.occurrences(20));
        }

        @Test
        public void onlyOneOcurrenceMiddleTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            Assertions.assertTrue(MyIndex.search(9));
            Assertions.assertEquals(1, MyIndex.occurrences(9));
        }

        @Test
        public void insertAtStartTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            MyIndex.insert(1);
            Assertions.assertTrue(MyIndex.search(1));
            Assertions.assertEquals(2, MyIndex.occurrences(1));
        }

        @Test
        public void insertAtEndTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            MyIndex.insert(15);
            Assertions.assertTrue(MyIndex.search(15));
            Assertions.assertEquals(3, MyIndex.occurrences(15));
        }

        @Test
        public void insertMiddleTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            MyIndex.insert(7);
            Assertions.assertTrue(MyIndex.search(7));
            Assertions.assertEquals(1, MyIndex.occurrences(7));
        }

        @Test
        public void insertInitialTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            MyIndex.insert(0);
            Assertions.assertTrue(MyIndex.search(0));
            Assertions.assertEquals(1, MyIndex.occurrences(0));
        }

        @Test
        public void insertNewTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            MyIndex.insert(11);
            Assertions.assertTrue(MyIndex.search(11));
            Assertions.assertEquals(1, MyIndex.occurrences(11));
        }

        @Test
        public void insertDuplicateTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            MyIndex.insert(10);
            Assertions.assertTrue(MyIndex.search(10));
            Assertions.assertEquals(2, MyIndex.occurrences(10));
        }

        @Test
        public void deleteInexistentTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            Assertions.assertFalse(MyIndex.search(40));
            Assertions.assertDoesNotThrow(() -> MyIndex.delete(40));
            Assertions.assertFalse(MyIndex.search(40));
        }

        @Test
        public void deleteAtStartTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            Assertions.assertTrue(MyIndex.search(1));
            Assertions.assertDoesNotThrow(() -> MyIndex.delete(1));
            Assertions.assertFalse(MyIndex.search(1));
        }

        @Test
        public void deleteAtEndTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3, 20});
            Assertions.assertTrue(MyIndex.search(20));
            Assertions.assertDoesNotThrow(() -> MyIndex.delete(20));
            Assertions.assertFalse(MyIndex.search(20));
        }

        @Test
        public void deleteMiddleTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            Assertions.assertTrue(MyIndex.search(8));
            Assertions.assertDoesNotThrow(() -> MyIndex.delete(8));
            Assertions.assertFalse(MyIndex.search(8));
        }

        @Test
        public void deleteDuplicateTest() {
            IndexService MyIndex = new IndexServiceImpl();
            MyIndex.initialize(new int[]{3, 10, 5, 8, 15, 3, 1, 9, 15, 12, 3});
            Assertions.assertTrue(MyIndex.search(3));
            Assertions.assertDoesNotThrow(() -> MyIndex.delete(3));
            Assertions.assertTrue(MyIndex.search(3));
        }

        @Test
        public void claseTest() {
            IndexService myIndex = new IndexServiceImpl();
            Assertions.assertEquals(0, myIndex.occurrences(10));
            myIndex.delete(10);       // ignora
            Assertions.assertFalse(myIndex.search(10));
            myIndex.insert(80);       // almacena [80]
            myIndex.insert(20);       // almacena [20, 80]
            myIndex.insert(80);       // almacena [20, 80, 80]
            Assertions.assertEquals(2, myIndex.occurrences(80));

            myIndex.initialize(new int[]{100, 50, 30, 50, 80, 100, 100, 30});    // el índice posee [30, 30, 50, 50, 80, 100, 100, 100]
            Assertions.assertFalse(myIndex.search(20));
            Assertions.assertTrue(myIndex.search(80));
            Assertions.assertEquals(2, myIndex.occurrences(50));
            myIndex.delete(50);
            Assertions.assertEquals(1, myIndex.occurrences(50));
        }

    }

