package SharingResources;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;
    public EvenChecker(IntGenerator g, int id) {
        generator = g;
        this.id = id;
    }

    @Override
    public void run() {
        while (!generator.isCancelled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                System.out.println(val + " is not even!");
                generator.cancel();
            }
        }
    }

    // test IntGenerator
    public static void test(IntGenerator g, int count) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for(int i = 0; i < count; i ++) {
            exec.execute(new EvenChecker(g, i));
        }
        exec.shutdown();
    }

    // default for count
    public static void test(IntGenerator g) {
        test(g, 10);
    }
}

