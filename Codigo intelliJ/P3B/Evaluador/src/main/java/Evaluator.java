import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Evaluator {

    Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");


    public Evaluator(){

    }

    private void operation(String token, Stack<Double> stack){
        double right = stack.pop();
        double left = stack.pop();

        switch (token) {
            case "+" -> stack.push(left + right);
            case "-" -> stack.push(left - right);
            case "/" -> stack.push(left / right);
            case "^" -> stack.push(Math.pow(left, right));
            default -> stack.push(left * right);
        }
    }



    private double readExpression(Scanner lineScanner) throws RuntimeException{
        Stack<Double> stack = new Stack<>();

        while(lineScanner.hasNext()){
            String token = lineScanner.next();

            if(token.matches("\\+|-|\\*|/|\\^")) {
                if(stack.size() < 2)
                    throw new RuntimeException("Expresion invalida: ");
                operation(token, stack);
            }
            else {
                try{
                    stack.push(Double.valueOf(token));
                }catch(NumberFormatException e){
                    throw new RuntimeException("Expresion invalida");
                }
            }
        }

        if(stack.size() != 1)
            throw new RuntimeException("Expresion invalida");

        return stack.pop();
    }

    public double evaluatePostFija(Scanner lineScanner){
        try {
            return readExpression(lineScanner);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }

    }

    public double evaluate(){
        System.out.println("Introduzca la expresion en notacion infija: ");
        inputScanner.hasNextLine();
        String line = inputScanner.nextLine();                                                      // Leo con el scanner que me saca el enter
        System.out.println(inFijaToPostFija(line));
        Scanner lineScanner = new Scanner(inFijaToPostFija(line)).useDelimiter("\\s+");             // Este scanner me lo parsea por los spacis
        return evaluatePostFija(lineScanner);
    }

    private final Map<String, Integer> mapping = new HashMap<>()
    {
        {put("+", 0); put("-", 1);put("*", 2);put("/", 3);put("^", 4);put("(", 5);put(")",6);}
    };

    private static final boolean[][] precedenceMatriz = {
            {true, true, false, false, false, false, true},
            {true, true, false, false, false, false ,true},
            {true, true, true, true, false, false, true},
            {true, true, true, true, false, false, true},
            {true, true, true, true, false, false, true},
            {false, false, false, false, false, false, false},
    };

    private boolean getPrecedence(String tope, String current){
        Integer topIndex = mapping.get(tope);
        Integer currentIndex = mapping.get(current);

        return precedenceMatriz[topIndex][currentIndex];
    }

    private String inFijaToPostFija(String line){
        Scanner inFijaScanner = new Scanner(line).useDelimiter("\\s+");
        Stack<String> stack = new Stack<>();
        String postFija = "";

        while(inFijaScanner.hasNext()){
            String token = inFijaScanner.next();
            if(token.matches("\\+|-|\\*|/|\\^|\\(|\\)")) {
                while(!stack.empty() && getPrecedence(stack.peek(), token)){
                    postFija += (stack.pop() + " ");
                }
                if(token.equals(")")){
                    if(!stack.empty() && stack.peek().equals("("))
                        stack.pop();
                    else
                        throw new RuntimeException("Expresion Invalida: Falta (");
                }
                else
                    stack.push(token);
            }
            else {
                try{
                    postFija += (Double.valueOf(token) + " ");
                }catch(RuntimeException e){
                    throw new RuntimeException("Expresion Invalida: Carcater invalido");
                }
            }
        }

        while(!stack.isEmpty()){
            if(stack.peek().equals("(")){
                throw new RuntimeException("Expresion invalida: Falta parentesis que cierre");
            }
            postFija += (stack.pop() + " ");
        }
        return postFija;
    }

}
