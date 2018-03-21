// Lock allows you to give up on trying to acquire a lock

package SharingResources;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
   private ReentrantLock lock = new ReentrantLock();
   public void untimed() {
       boolean captured = lock.tryLock();
       try {
           System.out.println("tryLock(): " + captured);
       } finally {
           if(captured) lock.unlock();
       }
   }

   public void timed() {
       boolean captured = false;
       try {
           captured = lock.tryLock(2, TimeUnit.SECONDS);
       } catch(InterruptedException e) {
           throw new RuntimeException(e);
       }

       try {
           System.out.println("tryLock(2, TimeUnit) :" + captured);
       } finally {
           if(captured) lock.unlock();
       }
   }

    public static void main(String[] args) {
        final AttemptLocking al = new AttemptLocking();
        al.untimed(); // ok
        al.timed(); // ok

        // new task to grab the lock
        new Thread() {
            { setDaemon(true); } // as a daemon to take the lock
            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        Thread.yield();
        al.untimed();
        al.timed(); // false to get the lock, you can do sth else
    }
}
