import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;


public class BinaryTree implements BinaryTreeService {

    private Node root;
    private String BRENCH = "|___";

    private Scanner inputScanner;

    public BinaryTree(String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        if (is == null)
            throw new FileNotFoundException(fileName);

        inputScanner = new Scanner(is);
        inputScanner.useDelimiter("\\s+");

        buildTree();

        inputScanner.close();
    }

    /*
    @Override
    public String toString(){
        if(root == null)
            return "?";
        // Por nivel (key), tengo una lista de valores de Node
        Map<Integer, List<String>> map = new HashMap<>();
        int level = 0;
        map.put(level, new ArrayList<>());
        map.get(level).add(root.data);

        if(root.left != null)
            root.left.levels(map, level + 1);
        if(root.right != null)
            root.right.levels(map, level + 1);

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; map.containsKey(i); i++){
            map.get(i).forEach((s) -> stringBuilder.append(s).append(" "));
        }
        return stringBuilder.toString();
    }*/

    //Solucion Iterativa
    @Override
    public void toFile(String filename){
        String toWrite = toString();
        try{
            FileWriter file = new FileWriter(filename);
            file.write(toWrite);
            file.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Solucion Iterativa
    @Override
    public String toString(){
        if(root == null)
            return "";
        Queue<Node> nextNodes = new LinkedList<>();
        int qty = 1;
        nextNodes.add(root);
        StringBuilder s = new StringBuilder();
        while(qty > 0){
            Node node = nextNodes.remove();
            if(node != null){
                qty-=1;
                s.append(node.data).append(" ");
                qty += node.left == null? 0 : 1;
                qty += node.right == null? 0 : 1;
                nextNodes.add(node.left);
                nextNodes.add(node.right);

            }else{
                s.append("?").append(" ");
                nextNodes.add(null);
                nextNodes.add(null);
            }
        }
        return s.toString();
    }

    public void preorder(){
        StringBuilder stringBuilder = new StringBuilder();
        if(root != null)
            root.preorder(stringBuilder);
        else
            stringBuilder.append("null");
        System.out.println("Pre orden: " + stringBuilder);
    }

    public void postorder(){
        StringBuilder stringBuilder = new StringBuilder();
        if(root != null)
            root.postorder(stringBuilder);
        else
            stringBuilder.append("null");
        System.out.println("Post orden: " + stringBuilder);
    }

    @Override
    public void printHierarchy() {
        if(root == null)
            System.out.println(BRENCH + "null");
        else {
            System.out.println(BRENCH + root.data);
            root.printHierarchy("   ");
        }
    }

    public void inorder(){
        StringBuilder stringBuilder = new StringBuilder();
        root.inorder(stringBuilder);
        System.out.println("In orden: " + stringBuilder);
    }

    private void buildTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        Queue<NodeHelper> pendingOps= new LinkedList<>();
        String token;

        root = new Node();
        pendingOps.add(new NodeHelper(root, (n)->n));

        while(inputScanner.hasNext()) {

            token = inputScanner.next();

            NodeHelper aPendingOp = pendingOps.remove();
            Node currentNode = aPendingOp.getNode();

            if ( token.equals("?") ) {
                // no hace falta poner en null al L o R porque ya esta con null

                // reservar el espacio en Queue aunque NULL no tiene hijos para aparear
                pendingOps.add( new NodeHelper(null, (n)->n) );  // como si hubiera izq
                pendingOps.add( new NodeHelper(null, (n)->n) );  // como si hubiera der
            }
            else {
                /*switch (aPendingOp.getAction())
                {
                    case LEFT:
                        currentNode = currentNode.setLeftTree(new Node());
                        break;
                    case RIGHT:
                        currentNode = currentNode.setRightTree(new Node());
                        break;
                }*/
                Function<Node, Node> anAction = aPendingOp.getAction();
                currentNode = anAction.apply(currentNode);

                // armo la info del izq, der o el root
                currentNode.data = token;

                // hijos se postergan
                pendingOps.add(new NodeHelper(currentNode, (node) -> node.setLeftTree(new Node())));
                pendingOps.add(new NodeHelper(currentNode, (node) -> node.setRightTree(new Node())));
            }


        }

        if (root.data == null)  // no entre al ciclo jamas
            root = null;

    }

    @Override
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(!(o instanceof BinaryTree))
            return false;

        BinaryTree toCompare = (BinaryTree) o;
        if((this.root!=null && toCompare.root==null) || (this.root==null && toCompare.root!=null))
            return false;
        if(this.root==null && toCompare.root==null)
            return true;

        return this.root.equals(toCompare.root);
    }


    @Override
    public int hashCode() {
        return Objects.hash(root, BRENCH, inputScanner);
    }

    // hasta el get() no se evalua
    class Node {
        private String data;
        private Node left;
        private Node right;

        public Node setLeftTree(Node aNode) {
            left = aNode;
            return left;
        }


        public Node setRightTree(Node aNode) {
            right= aNode;
            return right;
        }


        public Node() {
            // TODO Auto-generated constructor stub
        }

        private boolean isLeaf() {
            return left == null && right == null;
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

        private void printHierarchy(String tabs){
            if(left != null) {
                System.out.println(tabs + BRENCH + left.data);
                if(!left.isLeaf())
                    left.printHierarchy(tabs + "    ");
            }
            else
                System.out.println(tabs + BRENCH + "null");

            if(right != null) {
                System.out.println(tabs + BRENCH + right.data);
                if(!right.isLeaf())
                    right.printHierarchy(tabs + "   ");
            }
            else
                System.out.println(tabs + BRENCH + "null");
        }

        /*
        public void levels(Map<Integer, List<String>> map, int level){
            map.putIfAbsent(level, new ArrayList<>());
            map.get(level).add(data);

            if(left != null)
                left.levels(map, level + 1);
            if(right != null)
                right.levels(map, level + 1);
        }
        */

        public boolean equals(Object o){
            if(this == o)
                return true;
            if(!(o instanceof Node))
                return false;

            Node toCompare = (Node) o;
            if(this.isLeaf() &&  toCompare.isLeaf())
                return this.data.equals(toCompare.data);
            if( (this.left!=null && toCompare.left==null) || (this.left==null && toCompare.left!=null))
                return false;
            if( (this.right!=null && toCompare.right==null) || (this.right==null && toCompare.right!=null))
                return false;

            // Si estoy aca, estoy seguro de que no estoy comparando hojas
            if(this.left==null && toCompare.left == null)
                return this.data.equals(toCompare.data) && this.right.equals(toCompare.right);
            if(this.right==null && toCompare.right == null)
                return this.data.equals(toCompare.data) && this.left.equals(toCompare.left);

            return this.data.equals(toCompare.data) && this.left.equals(toCompare.left) && this.right.equals(toCompare.right);
        }
    }  


    static class NodeHelper {

        static enum Action { LEFT, RIGHT, CONSUMIR };

        private Node aNode;
        //private Action anAction;
        private Function<Node, Node> anAction;

        public NodeHelper(Node aNode, Function<Node, Node> anAction ) {
            this.aNode= aNode;
            this.anAction= anAction;
        }

        public Node getNode() {
            return aNode;
        }

        public Function<Node, Node> getAction() {
            return anAction;
        }

    }



    public static void main(String[] args) throws FileNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        System.out.println("___________________________");
        System.out.println("data0_1.txt");
        BinaryTreeService rta = new BinaryTree("data0_1.txt");
        System.out.println(rta);
        rta.toFile("data0_1-bis.txt");
        //rta.preorder();
        //rta.postorder();
        //rta.printHierarchy();
        System.out.println("___________________________");
        System.out.println("data0_2.txt");
        BinaryTreeService rta2 = new BinaryTree("data0_2.txt");
        System.out.println(rta2);
        rta2.toFile("data0_2-bis.txt");
        //rta2.preorder();
        //rta2.postorder();
        //rta2.printHierarchy();
        System.out.println("___________________________");
        System.out.println("data0_3.txt");
        BinaryTreeService rta3 = new BinaryTree("data0_3.txt");
        System.out.println(rta3);
        rta3.toFile("data0_3-bis.txt");
        //rta3.preorder();
        //rta3.postorder();
        //rta3.printHierarchy();
    }

}