public class MyTimerJoda {

    private Long start_time, ms;
    private static final Long MS_DAY = 86400000L;
    private static final Long MS_HOUR = 3600000L;
    private static final Long MS_MIN = 60000L;

    public MyTimerJoda(){
        new MyTimerJoda(System.currentTimeMillis());
    }

    public MyTimerJoda(Long start_time){
        this.start_time = start_time;
    }

    public void stop(Long end_time){
        ms = end_time - start_time;
    }

    public void stop(){
        if(start_time == null){
            throw new RuntimeException();
        }
        stop(System.currentTimeMillis());
    }

    public String toString(){
        if(ms == null || ms < 0){
            throw new RuntimeException();
        }
        return String.format("(%d ms) %d dia %d hs %d min %.3f s", ms, ms/MS_DAY, (ms%MS_DAY)/MS_HOUR, (ms%MS_HOUR)/MS_MIN, (ms%MS_MIN)/1000.0);
    }

}
