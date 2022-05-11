public class QGramMain {
    public static void main(String[] args) {
        QGram q = new QGram(2);

        q.printTokens("alal");


        double value = q.similarity("salesal", "alale");
        System.out.println(value);
    }
}
