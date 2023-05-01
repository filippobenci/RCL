import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainReaderWriter {
    public static void main(String[] args) {

        final int n_writer = 20;
        final int n_reader = 20;

        //Counter c = new Counter();
        //Counter c = new ReentrantLockCounter();
        Counter c = new ReadWriteLockCounter();

        //ExecutorService threadPool = Executors.newCachedThreadPool();
        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        long time1 = System.currentTimeMillis();
        for(int i = 0; i < n_writer; i++){
            threadPool.execute(new Writer(c));
        }

        for(int i = 0; i < n_reader; i++){
            threadPool.execute(new Reader(c));
        }

        threadPool.shutdown();

        try {
            while(!threadPool.isTerminated()){
                threadPool.awaitTermination(5, TimeUnit.SECONDS);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.printf("Tempo impiegato: %d ms", System.currentTimeMillis() - time1 );

    }
}


