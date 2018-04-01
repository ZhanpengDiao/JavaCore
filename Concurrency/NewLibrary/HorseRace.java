// Horse racing simulation by using CyclicBarrier
// very funny

package NewLibrary;

import java.util.Random;
import java.util.concurrent.*;
import java.util.*;

class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private int strides = 0;
    private static Random rand = new Random();
    private static CyclicBarrier barrier;

    public Horse(CyclicBarrier barrier) { this.barrier = barrier; }
    public synchronized int getStrides() { return strides; }

    @Override
    public void run() {
        try{
            while(!Thread.interrupted()) {
               synchronized(this) {
                  strides += rand.nextInt(3); // 0, 1 or 2
               }
               barrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
    public String toString() { return "Horse " + id + " " ; }
    public String tracks() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < getStrides(); i ++) {
            sb.append("*");
        }
        sb.append(id);
        return sb.toString();
    }
}

public class HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<Horse>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    public HorseRace(int nHorses, final int pause) {
        // Runnable will be automatically executed when the count reaches zero
        // (when all horses have moved forward)
        barrier = new CyclicBarrier(7, () -> {
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < FINISH_LINE; i ++) sb.append("=");
            System.out.println(sb);
            for(Horse h: horses) System.out.println(h.tracks());
            for(Horse h: horses) {
                if(h.getStrides() >= FINISH_LINE) {
                    System.out.println(h + "won!");
                    exec.shutdownNow();
                    return;
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(pause);
            } catch (InterruptedException e) {
                System.out.println("Barrier-action sleep interrupted");
            }
        });
        for(int i = 0; i < nHorses; i++) {
            Horse h = new Horse(barrier);
            horses.add(h);
            exec.execute(h);
        }
    }

    public static void main(String[] args) {
        int nHorse = 7;
        int pause = 200;
        new HorseRace(nHorse, pause);
    }
}
