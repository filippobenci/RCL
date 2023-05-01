import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.net.*;

public class WebServer {
    
    final static int WORKERS = 1;   // Numero di workers del threadpool (nel caso sia del tipo "fixed")
    final static int PORT = 6789;   // Porta utilizzata dal server per stare in ascolto

    public static void main(String[] args) {

        int myPort = PORT;
        if(args.length > 0){
            try {
                myPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException ex) {
                System.out.println("Il numero di porta deve essere un intero.\n");
                System.exit(-1);
            }
        }

        // dichiarazione del threadpool per la gestione delle richieste
        ExecutorService pool = Executors.newFixedThreadPool(WORKERS);

        try (ServerSocket server = new ServerSocket(myPort)){
            System.out.println("Il web server è in ascolto sulla porta: " + myPort);
            while(true){
                // rimane in attesa di richieste di connessione
                Socket client = server.accept();
                // invio al threadpool la richiesta di gestione del client
                pool.execute(new RequestManager(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            pool.shutdown();
            try{
                while(!pool.isTerminated()){
                    pool.awaitTermination(60, TimeUnit.SECONDS);
                }
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        } 

    }
        
}


