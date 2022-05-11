import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.*;

public class Tests {

    @Test
    public void testInitialize(){
        try {
            IndexService indexService = new IndexServiceImpl();
            indexService.initialize(null);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testOccurrences(){
        int[] elements = {1,2,3,4,4,4,5,5,6,7,8,9,10};
        IndexService indexService = new IndexServiceImpl();
        indexService.initialize(elements);
        Assertions.assertEquals(indexService.occurrences(0), 0);
        Assertions.assertEquals(indexService.occurrences(1), 1);
        Assertions.assertEquals(indexService.occurrences(2), 1);
        Assertions.assertEquals(indexService.occurrences(3), 1);
        Assertions.assertEquals(indexService.occurrences(4), 3);
        Assertions.assertEquals(indexService.occurrences(5), 2);
        Assertions.assertEquals(indexService.occurrences(6), 1);
        Assertions.assertEquals(indexService.occurrences(7), 1);
        Assertions.assertEquals(indexService.occurrences(8), 1);
        Assertions.assertEquals(indexService.occurrences(9), 1);
        Assertions.assertEquals(indexService.occurrences(10), 1);
        Assertions.assertEquals(indexService.occurrences(11), 0);
        int[] elements2 = {10,9,8,7,6,5,4,4,5,4,3,2,1};
        IndexService indexService2 = new IndexServiceImpl();
        indexService2.initialize(elements2);
        Assertions.assertEquals(indexService2.occurrences(0), 0);
        Assertions.assertEquals(indexService2.occurrences(1), 1);
        Assertions.assertEquals(indexService2.occurrences(2), 1);
        Assertions.assertEquals(indexService2.occurrences(3), 1);
        Assertions.assertEquals(indexService2.occurrences(4), 3);
        Assertions.assertEquals(indexService2.occurrences(5), 2);
        Assertions.assertEquals(indexService2.occurrences(6), 1);
        Assertions.assertEquals(indexService2.occurrences(7), 1);
        Assertions.assertEquals(indexService2.occurrences(8), 1);
        Assertions.assertEquals(indexService2.occurrences(9), 1);
        Assertions.assertEquals(indexService2.occurrences(10), 1);
        Assertions.assertEquals(indexService2.occurrences(11), 0);
    }


    @Test
    public void testInsert(){
        int[] elements = {1,2,3,4,4,4,5,5,6,7,8,9,10};
        IndexService indexService = new IndexServiceImpl();
        indexService.initialize(elements);

        for(int i = 0; i < 113; i++, indexService.insert(1)){
            //Consulto desde la base si esta y cuantos hay
            Assertions.assertTrue(indexService.search(1));
            Assertions.assertEquals(indexService.occurrences(1), i + 1);
        }
    }

    @Test
    public void testDelete(){
        int[] elements = {4,4,4,4,4,4,4,4,4,4,4};
        IndexService indexService = new IndexServiceImpl();
        indexService.initialize(elements);

        for(int i = elements.length; i >= 1; i--, indexService.delete(4)){
            //Consulto desde la base si esta y cuantos hay
            Assertions.assertTrue(indexService.search(4));
            Assertions.assertEquals(indexService.occurrences(4), i);
        }
    }

    @Test
    public void testSearch(){
        int[] elements = {1,2,3,4,5,6,7,8,9,10};
        IndexService indexService = new IndexServiceImpl();
        indexService.initialize(elements);

        for(int i = 1; i <= elements.length ; i++){
            //Consulto desde la base si esta y cuantos hay
            Assertions.assertTrue(indexService.search(i));
        }
    }

}

