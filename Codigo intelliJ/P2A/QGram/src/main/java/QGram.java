import java.util.HashMap;
import java.util.Map;

public class QGram {

    private final static int DEFAULT_Q = 3;
    private int Q;
    private String for_tokens;

    public QGram(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        Q = n;
        StringBuilder s = new StringBuilder();
        for (int i = 1; i < Q; i++) {
            s.append("#");
        }
        for_tokens = new String(s);
        System.out.println(for_tokens);
    }

    public QGram() {
        this(DEFAULT_Q);
    }

    private Map<String, Integer> getTokens(String s) {

        Map<String, Integer> tokens = new HashMap<>();

        if (s == null || s.length() == 0) {
            return tokens;
        }

        int substrings = s.length() + Q - 1;
        s = for_tokens + s + for_tokens;

        for (int i = 0; i < substrings; ++i) {
            tokens.merge(s.substring(i, i + Q), 1, Integer::sum);
        }
        return tokens;
    }

    public void printTokens(String s) {
        Map<String, Integer> tokens = getTokens(s);
        System.out.println("Tokens for " + s);
        System.out.println("Token - ocurrences");
        for (Map.Entry<String, Integer> t : tokens.entrySet()) {
            System.out.println(String.format(" %s - %2d ", t.getKey(), t.getValue()));
        }
    }

    public double similarity(String s1, String s2) {
        int substrings1, substrings2;

        if (s1 == null || s1.length() == 0) {
            substrings1 = 0;
        } else {
            substrings1 = s1.length() + Q - 1;
        }

        if (s2 == null || s2.length() == 0) {
            substrings2 = 0;
        } else {
            substrings2 = s2.length() + Q - 1;
        }

        if ((substrings1 + substrings2) == 0) {
            return 1;
        }

        int totalSubstrings = substrings1 + substrings2;
        Map<String, Integer> tokens1 = getTokens(s1);
        Map<String, Integer> tokens2 = getTokens(s2);

        int sameTokens = 0;
        for (Map.Entry<String, Integer> t1 : tokens1.entrySet()) {
            sameTokens += Math.min(tokens2.getOrDefault(t1.getKey(), 0), t1.getValue());
        }

        return 2 * sameTokens / (double) totalSubstrings;
    }
}
