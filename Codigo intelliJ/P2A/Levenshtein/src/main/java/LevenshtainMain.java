public class LevenshtainMain {
    public static void main(String[] args) {
        System.out.println(Levenshtein.distance("Big data", "Bigdaa"));
        System.out.println(Levenshtein.normalizedSimilarity("big data", "bigdaa"));
    }
}
