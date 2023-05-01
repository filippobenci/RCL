import java.util.concurrent.Callable;

public class Power implements Callable <Double> {

    private int exp;
    private double base;

    public Power (double b, int e){
        this.base = b;
        this.exp = e;
    }

    @Override
    public Double call(){
        System.out.format("Esecuzione %d^%d in %l", this.base, this.exp, Thread.currentThread().threadId());
        return Math.pow(this.base, this.exp);
    }
}
