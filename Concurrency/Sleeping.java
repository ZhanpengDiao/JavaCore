import java.util.concurrent.*;

public class Sleeping extends LiftOff{
    public void run() {
        try {
            while(countDown-- > 0) {
                System.out.print(status());
//                Thread.sleep(100);
                TimeUnit.MILLISECONDS.sleep(100); // new style
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < 5; i ++) {
            exec.execute(new Sleeping());
        }
        exec.shutdown();
    }
}
