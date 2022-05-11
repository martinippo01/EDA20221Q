public class Main {
    public static void main(String[] args) {
        Soundex soundex = new Soundex("threshold");
        System.out.println(Soundex.getCode("threshold"));
        double s1= Soundex.similarity( "threshold", "hold"); //debe devolver el double 0.0
        System.out.println(s1);
        s1= new Soundex("phone ").similarity( "foun"); //debe devolver el double 0.75
        System.out.println(s1);
    }
}
