// simulate the dining philosophers problem of Edsger Dijkstra

package DeadLock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable{
    private Chopstick left, right;
    private final int id;
    private final int ponderFactor;
    private Random rand = new Random();

    public Philosopher(Chopstick left, Chopstick right, int id, int ponder) {
        this.left = left;
        this.right = right;
        this.id = id;
        ponderFactor = ponder;
    }

    private void pause() throws InterruptedException {
        if(ponderFactor == 0) return;
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                System.out.println(this + " thinking");
                pause();
                // become hungry
                System.out.println(this + " grab right");
                right.take();
                System.out.println(this + " grab left");
                left.take();
                System.out.println(this + " eat");
                pause();
                right.drop();
                left.drop();
            }
        } catch(InterruptedException e) {
            System.out.println();
        }
    }
    public String toString() { return "Philosopher " + id; }
}
