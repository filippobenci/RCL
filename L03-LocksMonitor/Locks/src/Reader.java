public class Reader implements Runnable {
    
    private Counter c;

    public Reader (Counter c){
        this.c = c;
    }

    @Override
    public void run () {
        System.out.println(c.get());
    }
}
