public class MyTimer {

    private Long start_time, ms;
    private static final Long MS_DAY = 86400000L;
    private static final Long MS_HOUR = 3600000L;
    private static final Long MS_MIN = 60000L;

    public MyTimer(){
        start_time = System.currentTimeMillis();
    }

    public void stop(){
        ms = System.currentTimeMillis() - start_time;
    }

    public String toString(){
        if(ms == null || ms < 0){
            throw new RuntimeException();
        }
        return String.format("(%d ms) %d dia %d hs %d min %.3f s", ms, ms/MS_DAY, (ms%MS_DAY)/MS_HOUR, (ms%MS_HOUR)/MS_MIN, (ms%MS_MIN)/1000.0);
    }

		/* Ejemplo de leticia para calcular la hora:
				long durationseg= (stop - start) / 1000; // ms => seg
				long ms= (stop - start) % 1000; // la parte decimal para ms
				long dias= durationseg / (24 * 60 * 60);
				long tmp= durationseg % (24 * 60 * 60);
				long horas= tmp / (60 * 60);
				tmp= tmp % (60 * 60);
				long min= tmp / 60;
				long seg= tmp % 60;
		*/

    //Podemos crear el siguiente méotodo para esperar un tiempo

    private static void esperar(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}