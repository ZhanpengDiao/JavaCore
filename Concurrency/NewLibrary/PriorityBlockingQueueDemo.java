package NewLibrary;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private Random rand = new Random();
    private static int counter = 0;
    private final int id = counter ++;
    private final int priority;
    protected static List<PrioritizedTask> sequence = new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public int compareTo(PrioritizedTask o) {
        return priority < o.priority ? 1 : (priority > o.priority ? -1 : 0); // good exp
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
        } catch (InterruptedException e) {
            // a way out
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("[%1$-3d]", priority) + " task " + id;
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }

    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService exec;
        public EndSentinel(ExecutorService exec) {
            super(-1);
            this.exec = exec;
        }

        @Override
        public void run() {
            int count = 0;
            for(PrioritizedTask t: sequence) {
                System.out.println(t.summary());
                if (++count % 5 == 0) { // amazing
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

class PrioritizedTaskProducer implements Runnable {
    private Random rand = new Random();
    private Queue<Runnable> q;
    private ExecutorService exec;
    public PrioritizedTaskProducer(Queue<Runnable> q, ExecutorService exec) {
        this.q = q;
        this.exec = exec;
    }

    @Override
    public void run() {
        // fill up with random priority
        for(int i = 0; i < 20; i ++) {
            q.add(new PrioritizedTask(rand.nextInt(10)));
            Thread.yield(); // transfer to consumer?
        }

        try {
            for(int i = 0; i < 10; i ++) {
                TimeUnit.MILLISECONDS.sleep(250);
                q.add(new PrioritizedTask(10));
            }
            for(int i = 0; i < 10; i ++) {
                q.add(new PrioritizedTask(i));
            }
            // add sentinel
            q.add(new PrioritizedTask.EndSentinel(exec));

        } catch (InterruptedException e) {
            // a way out
        }
        System.out.println("Finished PrioritizedTaskProducer");
    }
}

class PrioritizedTaskConsumer implements Runnable {
    private PriorityBlockingQueue<Runnable> q;
    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // use current thread to run
                q.take().run();
            }
        } catch (InterruptedException e) {
            // a way out
        }
        System.out.println("Finished PriortizedTaskConsumer");
    }
}

public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        Random rand = new Random();
        ExecutorService exec = Executors.newCachedThreadPool();
        PriorityBlockingQueue<Runnable> q = new PriorityBlockingQueue<>();
        exec.execute(new PrioritizedTaskProducer(q, exec));
        exec.execute(new PrioritizedTaskConsumer(q));
    }
}
