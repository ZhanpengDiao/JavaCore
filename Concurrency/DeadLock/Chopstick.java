// simulate the dining philosophers problem of Edsger Dijkstra

package DeadLock;

public class Chopstick {
    private boolean taken = false;
    public synchronized void take() throws InterruptedException {
        while(taken) {
            wait();
        }
        taken = true;
    }
    public synchronized void drop() {
        taken = false;
        notifyAll();
    }
}
