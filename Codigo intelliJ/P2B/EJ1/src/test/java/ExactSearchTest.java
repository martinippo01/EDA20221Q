import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExactSearchTest {

    @Test
    public void mainTest(){
        char[] target = "abracadabra".toCharArray();
        char[] query = "ra".toCharArray();
        assertEquals(ExactSearch.indexOf(query, target), 2);

        target = "abracadabra".toCharArray();
        query = "aba".toCharArray();
        assertEquals(ExactSearch.indexOf(query, target), -1);

        target = "abracadabra".toCharArray();
        query = "abra".toCharArray();
        assertEquals(ExactSearch.indexOf(query, target), 0);
    }


}
