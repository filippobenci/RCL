public class Writer implements Runnable {

    private Counter c;

    public Writer (Counter c){
        this.c = c;
    }
    
    @Override
    public void run (){
        c.increment();
    }
}
