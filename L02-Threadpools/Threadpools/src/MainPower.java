import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainPower {
    
    public static void main(String[] args) {

        final double base = 2;
        final int it = 50;
        double result = 0;

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        ArrayList<Future<Double>> runningTasks = new ArrayList<Future<Double>>();

        for(int i = 2; i < it; i++){
            runningTasks.add(threadPool.submit(new Power(base, i)));
        }
        
        try {
            for(Future<Double>t: runningTasks){
                result += t.get();
            }
            System.out.println("Result is: " + result);
        } 
        catch (ExecutionException e) {
            System.out.println("Some thread has failed");
        }
        catch (InterruptedException e){
            System.out.println("Interrupted while waiting");
        }
        finally{
            threadPool.shutdown();
        }

    }
}
