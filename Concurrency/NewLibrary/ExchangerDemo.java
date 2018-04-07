package NewLibrary;

import Generator.*;
import java.util.concurrent.*;
import java.util.*;

class ExchangerProducer<T> implements Runnable {
    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    ExchangerProducer(Exchanger<List<T>> exchg, Generator<T> gen, List<T> holder) {
        exchanger = exchg;
        generator = gen;
        this.holder = holder;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                for(int i = 0; i < ExchangerDemo.size; i++) {
                    holder.add(generator.next());
                }
                // Exchange full for empty
                holder = exchanger.exchange(holder);
            }
        } catch(InterruptedException e) {
            // way out
        }
    }
}

class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;
    ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder) {
        exchanger = ex;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                holder = exchanger.exchange(holder); // blocked until called in producer
                for(T x: holder) {
                    value = x; // Fetch out value
                    holder.remove(x);
                }
            }
        } catch(InterruptedException e) {
            // way ot
        }
        System.out.println("Final value: " + value);
    }
}


public class ExchangerDemo {
    static int size = 10;
    static int delay = 5; // Seconds
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> xc = new Exchanger<List<Fat>>();
        List<Fat>
                producerList = new CopyOnWriteArrayList<>(),
                consumerList = new CopyOnWriteArrayList<>(); // concurrency safe
        exec.execute(new ExchangerProducer<Fat>(xc, BasicGenerator.create(Fat.class), producerList));
        exec.execute(new ExchangerConsumer<>(xc, consumerList));
        TimeUnit.SECONDS.sleep(delay);
        exec.shutdownNow();
    }
}
