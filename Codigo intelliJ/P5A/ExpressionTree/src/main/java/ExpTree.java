import java.util.*;


public class ExpTree implements ExpressionService {

    private Node root;

    public ExpTree(){
        System.out.print("Introduzca la expresión en notación infija con todos los paréntesis y blancos: ");

        // token analyzer
        Scanner inputScanner = new Scanner(System.in).useDelimiter("\\n");
        String line= inputScanner.nextLine();
        inputScanner.close();

        buildTree(line);
    }

    private void buildTree(String line) {
        // space separator among tokens
        Scanner lineScanner = new Scanner(line).useDelimiter("\\s+");
        root = new Node(lineScanner);
        lineScanner.close();
    }

    @Override
    public double eval() {
        return root.eval();
    }

    @Override
    public void preorder() {
        StringBuilder stringBuilder = new StringBuilder();
        root.preorder(stringBuilder);
        System.out.println("Pre orden: " + stringBuilder);
    }

    @Override
    public void inorder() {
        StringBuilder stringBuilder = new StringBuilder();
        root.inorder(stringBuilder);
        System.out.println("In orden: " + stringBuilder);
    }

    @Override
    public void postorder() {
        StringBuilder stringBuilder = new StringBuilder();
        root.postorder(stringBuilder);
        System.out.println("Post orden: " + stringBuilder);
    }

    public void dump() {
        System.out.println();
        root.dump(0);
    }

    static final class Node {
        private String data;
        private Node left, right;

        private Scanner lineScanner;

        public Node(Scanner theLineScanner) {
            lineScanner = theLineScanner;

            if(!lineScanner.hasNext())
                throw new RuntimeException("Bad expression");
            Node auxi = buildExpression();
            data = auxi.data;
            left = auxi.left;
            right = auxi.right;

            if (lineScanner.hasNext() )
                throw new RuntimeException("Bad expression");
        }

        private Node(){
        }

        private Node buildExpression(){
            Node toReturn = new Node();

            if(lineScanner.hasNext("\\(")){
                lineScanner.next();

                if(!lineScanner.hasNext())
                    throw new RuntimeException("Bad expression");
                // Expresion a la izquierda
                toReturn.left = buildExpression();

                if(!lineScanner.hasNext())
                    throw new RuntimeException("Bad expression");
                String operator = lineScanner.next();
                if(!Utils.isOperator(operator))
                    throw new UnsupportedOperationException("Invalid operator: " + operator);
                toReturn.data = operator;


                if(!lineScanner.hasNext())
                    throw new RuntimeException("Bad expression");
                // Expresion a la derecha
                toReturn.right = buildExpression();

                String lastParenthesis = lineScanner.next();
                if(!Utils.isCloseParenthesis(lastParenthesis))
                    throw new IncorrectParenthesisException(") missing");
            }
            else {
                if(!lineScanner.hasNext())
                    throw new RuntimeException("Bad expression");
                String operand = lineScanner.next();
                if(!Utils.isConstant(operand))
                    throw new OperandException("Invalid operand: " + operand);
                toReturn.data = operand;

                //Nodos en null pues es hoja
                toReturn.left = null;
                toReturn.right = null;
            }

            return toReturn;
        }

        public void dump(int count) {
            if (right != null) {
                this.right.dump(count + 1);
            }
            System.out.println("    ".repeat(count) + this.data);
            if (left != null) {
                this.left.dump(count + 1);
            }
        }

        public StringBuilder preorder(StringBuilder string) {
            string.append(data).append(" ");
            if(left != null)
                left.preorder(string);
            if(right != null)
                right.preorder(string);
            return string;
        }

        public StringBuilder inorder(StringBuilder string) {
            if(left != null) {
                string.append("( ");
                left.inorder(string);
            }
            string.append(data).append(" ");
            if(right != null) {
                right.inorder(string);
                string.append(") ");
            }
            return string;
        }

        public StringBuilder postorder(StringBuilder string) {
            if(left != null)
                left.postorder(string);
            if(right != null)
                right.postorder(string);
            string.append(data).append(" ");
            return string;
        }

        public double eval(){
            if(left == null || right == null)
                return Double.parseDouble(data);
            return operation(data, left.eval(), right.eval());
        }

        private double operation(String token, double left, double right){
            switch (token) {
                case "+" -> {
                    return left + right;
                }
                case "-" ->{
                    return left - right;
                }
                case "/" -> {
                    return left / right;
                }
                case "^" -> {
                    return Math.pow(left, right);
                }
                default -> {
                    return left * right;
                }
            }
        }
    }  // end Node class



    // hasta que armen los testeos
    public static void main(String[] args) {
        try {
            ExpressionService myExp = new ExpTree();
            System.out.println("________________________________");
            System.out.println("Arbol grafico (Con mucha imaginacion):");
            myExp.dump();
            System.out.println("________________________________");
            System.out.println("Notaciones:");
            System.out.print("  -");
            myExp.preorder();
            System.out.print("  -");
            myExp.postorder();
            System.out.print("  -");
            myExp.inorder();
            System.out.println("________________________________");
            System.out.println("Evaluacion:");
            System.out.print("  -");
            System.out.println(myExp.eval());
        }catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }



    }

}  // end ExpTree class