public class MyTimerTest {
    public static void main(String[] args) {
        MyTimer timer = new MyTimer();
        // bla bla bla ….. aca se invocaría el algoritmo cuyo tiempo de ejecución quiere medirse

        final Integer TOPE = 150;

        for(Integer i = 0; i < TOPE; i++){
            for(Integer j = 0; j < TOPE; j++){
              for(Integer k = 0; k < TOPE; k++){
                    System.out.println("Hola");
                }
            }
        }

        timer.stop();
        System.out.println(timer);
    }
}
