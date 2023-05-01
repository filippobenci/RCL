import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SalaBiglietteria {
    
    private final int emettitrici = 5;
    private final int maxCapienza = 10;
    private final ExecutorService threadpoolEmettitrici;
    private int viaggiatoriServiti;

    public SalaBiglietteria (){
        this.threadpoolEmettitrici = new ThreadPoolExecutor(emettitrici, emettitrici, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(maxCapienza));
    }

    public void serviViaggiatore (Viaggiatore v) throws RejectedExecutionException {
        try {
            this.threadpoolEmettitrici.execute(v);
        } catch (RejectedExecutionException e) {
            throw new RejectedExecutionException();
        }
        viaggiatoriServiti++;
    }

    public int getViaggiatoriServiti(){
        return viaggiatoriServiti;
    }

    public void chiudiSala (long timeout){
        this.threadpoolEmettitrici.shutdown();
        try {
            while(!threadpoolEmettitrici.isTerminated()){
                this.threadpoolEmettitrici.awaitTermination(timeout, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            System.out.println("awaitTermination interrotta");
        }
    }
}
