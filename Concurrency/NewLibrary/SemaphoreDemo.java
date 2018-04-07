// Semaphore used to restrict the number of access in Pool
// see Pool class

package NewLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class CheckoutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;

    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + "checked out " + item);
            TimeUnit.SECONDS.sleep(2);
            System.out.println(this + "checking in " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {
            // way out
        }
    }

    @Override
    public String toString() {
        return "CheckoutTask " + id + " ";
    }
}

public class SemaphoreDemo {
    final static int SIZE = 25;

    public static void main(String[] args) throws InterruptedException {
        final Pool<Fat> p = new Pool<Fat>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < SIZE; i ++) {
            exec.execute(new CheckoutTask<Fat>(p));
        }
        System.out.println("All CheckoutTasks created");
        List<Fat> list = new ArrayList<>();

        for(int i = 0; i < SIZE; i ++) { // take all items of pool
            Fat f = p.checkOut();
            System.out.println(i + ": main() thread checked out");
            f.operate();
            list.add(f);
        }

        Future<?> blocked = exec.submit(new Runnable() {
            @Override
            public void run() {
               try {
                   // semaphore prevent additional checkout
                   p.checkOut();
               } catch (InterruptedException e) {
                   System.out.println("checkOut() Interrupted");
               }
            }
        });

        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true); // break out of blocked call
        System.out.println("Checking in objects in " + list);
        for(Fat f: list) p.checkIn(f);
        exec.shutdown();
    }
}
