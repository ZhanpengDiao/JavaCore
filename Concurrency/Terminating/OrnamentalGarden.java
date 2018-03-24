package Terminating;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Count {
    private int count = 0;
    private Random rand = new Random();
    public synchronized int increment() {
        int temp = count; // use of temp and yield() exaggerate the potential of failure
        if(rand.nextBoolean()) Thread.yield();
        return (count = ++temp);
    }
    public synchronized int value() { return count; }
}

class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<>();
    private int number = 0;
    // don't need synchronization to read
    private final int id;
    private static volatile boolean canceled = false;
    // atomic operation on a volatile field
    public static void cancel() { canceled = true; }
    public Entrance(int id) {
        this.id = id;
        // keep list tasks, prevent garbage collection.
        entrances.add(this);
    }
    @Override
    public void run() {
        while(!canceled) {
            synchronized (this) { // where sync in here is needed?
                ++number;
            }
            System.out.println(this + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted");
            }
        }
        System.out.println("stopping " + this);
    }

    public synchronized int getValue() { return number; }
    public String toString() {
        return "Entrance " + id + ": " + getValue();
    }
    public static int getTotalCount() {
        return count.value();
    }
    public static int sumEntrances() {
        int sum = 0;
        for(Entrance en: entrances) {
            sum += en.getValue();
        }
        return sum;
    }
}

public class OrnamentalGarden {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0 ; i < 5 ; i ++) {
            exec.execute(new Entrance(i));
        }
        // run for a while, then stop and collect the data
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();
        if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
            System.out.println("some tasks were still running");
        System.out.println("total: " + Entrance.getTotalCount());
        System.out.println("sum of entrances: " + Entrance.sumEntrances() );
    }
}
