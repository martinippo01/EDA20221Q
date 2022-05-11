import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class Main {
    // Levenshtein default package
    public static void main(String[] args) {
        Metaphone metaphone = new Metaphone();
        metaphone.setMaxCodeLen(10);
        String encode1 = metaphone.encode("brooklyn");
        System.out.println("Metaphone(brooklyn): " + encode1);
        String encode2 = metaphone.encode("clean");
        System.out.println("Metaphone(clean): " + encode2);
        System.out.println(Levenshtein.distance(encode1,encode2));
        System.out.println(Levenshtein.normalizedSimilarity(encode1,encode2));
    }
}
