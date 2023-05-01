

public class CalculatePI implements Runnable {                              // Per creare ed attivare il thread, si crea una classe che implementa l'interfaccia Runnable che servirà per definira il task da eseguire

    private double accuracy;
    private double pi = 0;
    private int i = 1;

    public CalculatePI (double acc){                                        // Costruttore
        this.accuracy = acc;
    }    

    public void run(){
        while (!Thread.interrupted()){                                      // Il thread continua ad effettuare il calcolo della serie finchè non riceve una richiesta di interruzione 
            double calc = Math.pow(-1, i+1) / (2* (i-1) + 1);               // Calcolo della serie di Gregory-Liebniz
            pi += calc;

            if (Math.abs(4*pi - Math.PI) < accuracy){                       // Se il valore dell'accuratezza scelto dall'utente è maggiore della differenza tra il valore stimato dalla serie e il valore di pigreco allora si esce dal ciclo
                System.out.println("La differenza tra il valore stimato dalla serie e il valore di pigreco è minore dell'accuratezza scelto:" + 4 * pi + "-"  + Math.PI + "=" + Math.abs(4*pi - Math.PI));
                return;
            }

            i++;
        }
        System.out.println("Il valore stimato dalla serie di Leibniz è:" + 4 * pi);
    }

}