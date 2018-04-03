// take the most "urgent" task (expired for the longest time)
// DelayQueue implements Delayed

package NewLibrary;

import java.util.*;
import java.util.concurrent.*;

class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter ++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();

    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS); // expected expiration time
        sequence.add(this); // add to list
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return trigger - System.nanoTime();
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask obj = (DelayedTask)o;
        if(trigger < obj.trigger) return -1;
        if(trigger > obj.trigger) return 1;
        return 0;
    }

    @Override
    public void run() {
        System.out.print(this + " ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask { // static class as the end sentinel // singleton
        private ExecutorService exec;
        public EndSentinel(int delay, ExecutorService e) {
            super(delay);
            exec = e;
        }

        @Override
        public void run() {
            for(DelayedTask t: sequence) {
                System.out.println(t.summary() + " ");
            }
            System.out.println();
            System.out.println(this + " Calling shutdownNow()");
            exec.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> q;
    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                q.take().run(); // take() will return null if no elem has expired
            }
        } catch (InterruptedException e) {
            // a way out
        }
        System.out.println("Finished DelaydeTaskConsumer");
    }
}

public class DelayedQueueDemo {
    public static void main(String[] args) {
        Random rand = new Random();
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> q = new DelayQueue<>();
        for(int i = 0; i < 20; i ++) {
            q.put(new DelayedTask((rand.nextInt(5000))));
        }
        // add stop point
        q.add(new DelayedTask.EndSentinel(5000, exec));
        exec.execute(new DelayedTaskConsumer(q));
    }
}
