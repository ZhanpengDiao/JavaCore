// rewrite MutexEvenGenerator with atomic class
// all forms of synchronization has been eliminated
// but it is safer to use lock/sync

package SharingResources;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicEvenGenerator extends IntGenerator {

    private AtomicInteger currentValue = new AtomicInteger(0);

    @Override
    public int next() {
        return currentValue.addAndGet(2);
    }

    public static void main(String[] args) {
        EvenChecker.test(new AtomicEvenGenerator());
    }
}
