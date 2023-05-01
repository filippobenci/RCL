import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);                      // Utilizzo l'oggetto scanner per leggere da tastiera l'input

        System.out.println("Inserisci l'accuratezza:");
        double acc = scan.nextDouble();

        System.out.println("Inserisci il tempo massimo di attesa");
        int time = scan.nextInt();

        scan.close();

        CalculatePI calculator = new CalculatePI(acc);                  // Creo un'istanza del task da eseguire
        Thread T = new Thread(calculator);                              // Passo il task al nuovo thread creato
        T.start();
        try {
            Thread.sleep(time);                                         // Il thread main attende il tempo di timeout definito dall'utente mettendosi in sleep 
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + "è stato interrotto durante la sleep");   
            return;
        }
        T.interrupt();                                                  // Una volta scaduto il timeout viene inviata un'interruzione
        
    }
}
