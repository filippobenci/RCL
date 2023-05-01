public class Viaggiatore implements Runnable{

    public static final int MAX_ATTESA = 1000;
    public final int id;

    public Viaggiatore (int identifier){
        this.id = identifier;
    }

    @Override
    public void run(){
        System.out.printf("Viaggiatore %d: sto acquistando un biglietto\n", this.id);
        try {
            Thread.sleep((int) Math.random() * MAX_ATTESA);
        } catch (InterruptedException e) {
            System.out.println("Viaggiatore " + this.id + " interrotto");
        }
        System.out.printf("Viaggiatore %d: ho acquistato un biglietto\n", this.id);
    }

    public int getId(){
        return id;
    }






}