import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class EvaluatorTest {

    public static void main(String[] args) throws Exception{
        InputStreamReader read=new InputStreamReader(System.in);
        BufferedReader in=new BufferedReader(read);
        for(boolean check = true; check; System.out.println("1: Continue 0: Stop"), check = (Integer.parseInt(in.readLine()) == 1)){
            System.out.println(new Evaluator().evaluate());
        }

    }
}
