// if a thread is a daemon, any thread it creates will be daemon
import java.util.concurrent.*;

class DaemonSpawn implements Runnable {
    @Override
    public void run() {
        while(true) {
            Thread.yield();
        }
    }
}

class Daemon implements Runnable{
    private Thread[] ts = new Thread[5];
    @Override
    public void run() {
        for(int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(new DaemonSpawn());
            ts[i].start();
            System.out.print("DaemonSpawn " + i + " started, ");
        }
        for(int i = 0; i < ts.length; i++) {
            System.out.print("ts[" + i + "].isDaemon() = " + ts[i].isDaemon() + ", ");
        }
        while(true) Thread.yield();
    }
}

public class Daemons {
    public static void main(String[] args) throws Exception {
        Thread d = new Thread(new Daemon());
        d.setDaemon(true); // the "main" thread is set as daemon
        d.start();
        TimeUnit.SECONDS.sleep(1);
    }
}
