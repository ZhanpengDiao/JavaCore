package Terminating;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class SleepBlocked implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch(InterruptedException e) {
            System.out.println("Interrupted");
        }
        System.out.println("Exiting SleepBlcoked.run()");
    }
}

class IOBlocked implements Runnable { // uninterruptible
    private InputStream in;
    public IOBlocked(InputStream in) { this.in = in; }
    @Override
    public void run() {
        try {
            System.out.println("waiting for read");
            in.read();
        } catch (IOException e) {
            if(Thread.currentThread().isInterrupted()) {
                System.out.println("Interrputed from blocked IO");
            } else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IOBlocked.run()");
    }
}

class SynchronizedBlocked implements Runnable { // interruptible
    public synchronized void f() { // get the lock (synchronized)
        System.out.println(Thread.currentThread());
        while(true) // never relseases lock
            Thread.yield();
    }

    public SynchronizedBlocked() {
        new Thread() {
            public void run() {
                f(); // lock acquired by this thread
            }
        }.start();
    }

    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}

public class Interrupting {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static void test(Runnable r) throws InterruptedException {
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + r.getClass().getName());
        f.cancel(true); // permission to call interrupt()
        System.out.println("Interrupt sent to " + r.getClass().getName());
    }
    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }
}
