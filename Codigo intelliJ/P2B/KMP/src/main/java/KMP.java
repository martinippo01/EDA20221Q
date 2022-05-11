import java.util.ArrayList;
import java.util.List;

public class KMP {

    //Implementaciones de newxt dadas por la catedra
    private static int[] nextComputation1(char[] query) {
        int[] next = new int[query.length];

        int border=0;  // Length of the current border

        int rec=1;
        while(rec < query.length){
            if(query[rec]!=query[border]){
                if(border!=0)
                    border=next[border-1];
                else
                    next[rec++]=0;
            }
            else{
                border++;
                next[rec]=border;
                rec++;
            }
        }
        return next;
    }
    private static int[] nextComputation2(char[] query) {
        int[] next = new int[query.length];
        next[0] = 0;     // Always. There's no proper border.
        int border = 0;  // Length of the current border
        for (int rec = 1; rec < query.length; rec++) {
            while ((border > 0) && (query[border] != query[rec]))
                border = next[border - 1];     // Improving previous computation
            if (query[border] == query[rec])
                border++;
            // else border = 0;  // redundant
            next[rec] = border;
        }
        return next;
    }

    //Implementando KMP
    public static int indexOf( char[] query, char[] target) {

        int rec = 0;
        int pquery = 0;

        int[] next = nextComputation2(query);

        while(rec < target.length && pquery < query.length){

            if(query [pquery] != target[rec]){
                if(pquery != 0)
                    pquery = next[pquery - 1];
                else
                    rec++;
            }
            else {
                rec++;
                pquery++;
            }
        }
        if(rec == target.length)
            return -1;
        else
            return rec - pquery;
    }

    //Utilizando KMP y next, hacemos un findAll
    public static List<Integer> findAll(char[] query, char[]target){
        int rec = 0;
        int pquery = 0;
        List<Integer> output = new ArrayList<>();


        int[] next = nextComputation2(query);

        while(rec < target.length){

            if(pquery == query.length){
                output.add(rec - pquery);
                pquery = next[pquery - 1];
            }
            else if(query [pquery] != target[rec]){
                if(pquery != 0)
                    pquery = next[pquery - 1];
                else
                    rec++;
            }
            else {
                rec++;
                pquery++;
            }
        }
        if(pquery == query.length){
            output.add(rec - pquery);
        }
        return output;
    }


}
