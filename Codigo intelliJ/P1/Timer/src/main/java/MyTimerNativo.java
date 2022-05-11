import java.time.Duration;
import java.time.Instant;
import java.time.Period;

public class MyTimerNativo {

    private Instant start;
    private Duration duration;
    private long ms;

    public MyTimerNativo(long start){
        if(start < 0){
            throw new RuntimeException("A timer cant start in a negative time");
        }
        this.start = Instant.ofEpochMilli(start);
    }

    public MyTimerNativo(){
        start = Instant.now();
    }

    public void stop(long end){
        if(duration!=null){
            throw new RuntimeException("This timer has already been stopped. Create a new one");
        }
        if(end < 0){
            throw new RuntimeException("A timer cant stop in a negative time");
        }
        if(start.toEpochMilli()>end){
            throw new RuntimeException("Stop must be greater or equal than starting time");
        }
        ms = end - start.toEpochMilli();
        duration = Duration.ofMillis(ms);
    }

    public void stop(){
        stop(Instant.now().toEpochMilli());
    }

    public String toString(){
        if(duration == null){
            throw new RuntimeException("Stop the timer first");
        }
        return String.format("(%d ms) %d days %d hs %d min %.3f s", ms, duration.getSeconds()/86400 , duration.getSeconds()/3600, duration.getSeconds()/60, (duration.getNano()/1000)/1000.0);
    }


}