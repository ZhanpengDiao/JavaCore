// make the Fibonacci Class iterable
import java.util.*;

public class FibonacciIterable extends Fibonacci implements Iterable<Integer> {
    private int n;
    public FibonacciIterable(int count) { n = count; }
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() { return n > 0; }

            @Override
            public Integer next() {
                n--;
                return FibonacciIterable.this.next();
            }
        };
    }

    public static void main(String[] args) {
        for(int i: new FibonacciIterable(10)) {
            System.out.println(i);
        }
    }
}
