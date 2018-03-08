
import java.util.concurrent.*;

public class DaemonFromFactory implements Runnable {
    @Override
    public void run() {
        try {
            while(true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + ": " + this);
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for(int i = 0; i < 10 ;i ++) {
            exec.execute(new DaemonFromFactory());
        }
        System.out.println("all daemons started");
        TimeUnit.MILLISECONDS.sleep(500);
    }
}
