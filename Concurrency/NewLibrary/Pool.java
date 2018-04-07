// semaphore: allows n tasks to access to resource  at the same time
// condition of use: obj is expensive to create

package NewLibrary;

import java.util.*;
import java.util.concurrent.Semaphore;

public class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<>();
    private volatile boolean[] checkedOut;
    private Semaphore available;

    public Pool(Class<T> classObject, int size) {
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size, true); // arg2: fairness
        // load pool with objects that can be checked out
        for(int i = 0; i < size; i ++) {
            try {
                // assumes a default constructor
                items.add(classObject.newInstance());
            } catch (IllegalAccessException e) {
                throw new RuntimeException();
            } catch (InstantiationException e) {
                throw new RuntimeException();
            }
        }
    }
    public T checkOut() throws InterruptedException {
        available.acquire(); // blocking the call if there are no more semaphore permits
        return getItem();
    }

    public void checkIn(T x) {
        if(releaseItem(x)) available.release();
    }

    private synchronized T getItem() {
        for(int i = 0; i < size; i ++) {
            if(!checkedOut[i]) {
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        return null; // semaphore prevent
    }

    private synchronized boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if(index == -1) return false;
        if(checkedOut[index]) {
            checkedOut[index] = false;
            return true;
        }
        return false;
    }
}
