public class Levenshtein {

    public static int distance(String wordA, String wordB){

        int[][] matrix = new int[wordA.length() + 1][wordB.length() + 1];

        for(int pos = 0; pos <= wordA.length(); pos++){
            matrix[pos][0]= pos;
        }

        for(int pos = 0; pos <= wordB.length(); pos++){
            matrix[0][pos]= pos;
        }

        for(int row = 1; row <= wordA.length(); row++){
            for(int collum = 1; collum <= wordB.length(); collum++){
                matrix[row][collum] = Math.min(matrix[row - 1][collum - 1] + ((wordA.charAt(row - 1) == wordB.charAt(collum - 1))? 0 : 1), Math.min(matrix[row - 1][collum] + 1, matrix[row][collum - 1] + 1));

            }
        }
        return matrix[wordA.length()][wordB.length()];
    }

    public static double normalizedSimilarity(String wordA, String wordB){
        return 1 - ( (double)distance(wordA, wordB) / Math.max(wordA.length(), wordB.length()));
    }

}
