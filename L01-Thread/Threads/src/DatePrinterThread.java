import java.util.Calendar;

public class DatePrinterThread extends Thread {
    public static void main(String[] args) {
        DatePrinterThread dateprinter = new DatePrinterThread();
        dateprinter.start();
        System.out.println(Thread.currentThread().getName());
    }

    public void run (){
        while (true){
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
