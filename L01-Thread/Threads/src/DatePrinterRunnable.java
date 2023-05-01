import java.util.Calendar;

public class DatePrinterRunnable implements Runnable {
    public static void main(String[] args) {
        DatePrinterRunnable datePrinter = new DatePrinterRunnable();
        Thread thread = new Thread(datePrinter);
        /* Oppure alternativamente 
         *      Thread thread = new Thread(new DatePrinterRunnable());
         */
        thread.start();
        System.out.println(Thread.currentThread().getName());
    }

@Override
public void run () {
    while(true){
        System.out.println(Calendar.getInstance().getTime());
        System.out.println(Thread.currentThread().getName());

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
             System.out.println("Sleep interrotta.");
                return;
            }
    }
}
}
