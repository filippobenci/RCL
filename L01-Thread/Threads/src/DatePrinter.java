import java.util.Calendar;

public class DatePrinter {

    public static void main(String[] args) {
        while(true){
            System.out.println(Calendar.getInstance().getTime());
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Sleep interrotta");
                return;
            }
        }
        // System.out.println(Thread.currentThread().getName());                // Istruzione mai raggiunta a causa del ciclo infinito
    }

}
