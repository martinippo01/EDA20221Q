public class MyTimerJodaTest {

    public static void main(String[] args){
        MyTimerJoda t1 = new MyTimerJoda(0L);
        t1.stop(86400000L);
        System.out.println(t1.toString());
    }

}
