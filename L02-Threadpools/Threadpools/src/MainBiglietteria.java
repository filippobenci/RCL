import java.util.concurrent.RejectedExecutionException;

public class MainBiglietteria {
    public static void main(String[] args) {
        final int n_viaggiatori = 50;
        final int max_attesa = 50;

        SalaBiglietteria sala = new SalaBiglietteria();

        for(int i = 0; i < n_viaggiatori; i++){
            Viaggiatore v = new Viaggiatore(i);
            try {
                sala.serviViaggiatore(v);
            } catch (RejectedExecutionException e) {
                System.out.printf("Traveler no. %d: sala esaurita.\n", v.getId());
            }
            try {
                Thread.sleep(max_attesa);
            } catch (InterruptedException e) {
                System.out.println("Sleep");
            }
        }
        sala.chiudiSala(5);
        System.out.println("Numero di viaggiatori serviti:" + sala.getViaggiatoriServiti());
    }
}
