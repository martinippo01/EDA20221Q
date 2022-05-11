import java.util.*;
/*
public class Soundex {

    private static final HashMap<Character, Character> pesoFonetico = new HashMap<Character, Character>(){{
        put('A', '0'); put('1', '0'); put('C', '2');put('D', '3');put('E', '0');put('F', '1');
        put('G', '2');put('H', '0');put('I', '0');put('J', '2');put('K', '2');put('M', '5');put('N', '5');
        put('L', '4');put('O', '0');put('P', '1');put('Q', '2');put('R', '6');put('S', '2');put('T', '3');
        put('U', '0');put('V', '1');put('W', '0');put('X', '2');put('Y', '0');put('Z', '2');
    }};

    private final StringBuilder input;
    private StringBuilder out = new StringBuilder("0000");

    public Soundex(String input){
        if(input == null)
            throw new IllegalArgumentException();
        this.input = new StringBuilder(input.toLowerCase().trim());
        System.out.println(input);
        validateInput();
        System.out.println(input);
        cleanInput();
        getCode();
    }


    private void getCode(){
        int last = pesoFonetico.get(input.charAt(0));
        for(int i = 1, pos = 1; i < input.length() && pos < 4; i ++){
            char current = pesoFonetico.get(input.charAt(i));
            if(current != 0 && current != last)
                out.setCharAt(pos++, current);
            last = current;
        }
    }

    private void cleanInput(){
        char[] string = input.toString().toCharArray();
        char last = string[0];
        for(int i = 1; i < input.length(); i++){
            if(Character.isLetter(string[i]))
                input.setCharAt(i, string[i]);
        }
    }

    private void validateInput(){
        if(input.length() == 0)
            throw new IllegalArgumentException();
    }

    public String representation(){
        return out.toString();
    }

}
*/

public class Soundex{

    private static final String[] codes = {"AEIOUYWH", "BFPV","CGJKQSXZ","DT","L","MN","R"};
    private String code;

    public Soundex(String s){
        code = getCode(s);
    }

    public double similarity(String s){
        return compareCodes(code, getCode(s));
    }

    public static double similarity(String s1, String s2){
        return compareCodes( getCode(s1) ,getCode(s2) );
    }

    private static double compareCodes(String c1, String c2){
        double equalChars = 0;
        for(int i=0 ; i<4 ; i++){
            if(c1.charAt(i) == c2.charAt(i))
                equalChars += 0.25;
        }
        return equalChars;
    }

    public static String getCode(String s){

        char[] OUT = {'0','0','0','0'};

        //Por si el string está vacío
        if(s==null || s==""){
            return new String(OUT);
        }

        char[] IN = s.toUpperCase().toCharArray();
        OUT[0] = IN[0];

        int count = 1;
        char current;
        char last = getMapping(IN[0]);
        for(int i = 1 ; i < IN.length && count < 4 ; i++){
            current = getMapping(IN[i]);
            if(current!='7'){
                if(current!='0' && current!=last){
                    OUT[count++] = current;
                }
                last=current;
            }
        }
        return new String(OUT);
    }

    private static char getMapping(char c){
        for (int i=0 ; i<codes.length ; i++) {
            if (codes[i].indexOf(c) != -1)
                return (char) (i + '0');
        }
        return '7';
    }

}
