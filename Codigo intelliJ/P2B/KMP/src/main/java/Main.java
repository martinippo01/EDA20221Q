public class Main {

    public static void main(String[] args) {
        String query, text;
        int pos;

        // debe dar 3
        query= "ABXABU";
        text= "ABXABXABUF";

        pos= KMP.indexOf(query.toCharArray(), text.toCharArray());
        System.out.println(String.format("%s in %s at pos %d\n", query, text, pos));


        // debe dar 5
        query= "ABAB";
        text= "SABASABABA";
        pos= KMP.indexOf(query.toCharArray(), text.toCharArray());
        System.out.println(String.format("%s in %s at pos %d\n", query, text, pos));


        // debe dar 0
        query= "ABAB";
        text= "ABABYYYY";
        pos= KMP.indexOf(query.toCharArray(), text.toCharArray());
        System.out.printf("%s in %s at pos %d\n%n", query, text, pos);


        // debe dar -1
        query= "ABAB";
        text= "ABAYYYA";
        pos= KMP.indexOf(query.toCharArray(), text.toCharArray());
        System.out.printf("%s in %s at pos %d\n%n", query, text, pos);


        //debería devolver [2, 17, 29]
        query= "no";
        String target= "sino se los digo no se si es nocivo";
        System.out.println(KMP.findAll(query.toCharArray(), target.toCharArray()));


        //debería devolver []
        query= "ni";
        target= "sino se los digo no se si es nocivo";
        //debería devolver un empty arraylist
        System.out.println(KMP.findAll(query.toCharArray(), target.toCharArray()));

        //debería devolver [0, 4, 5, 6]
        query= "aaa";
        target= "aaabaaaaab";
        System.out.println(KMP.findAll(query.toCharArray(), target.toCharArray()));
    }

}
