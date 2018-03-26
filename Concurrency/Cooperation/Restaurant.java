// simulate restaurant // task handshaking

package Cooperation;

import javax.lang.model.type.ExecutableType;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Meal {
    private final int orderNum;
    public Meal(int orderNum) { this.orderNum = orderNum; }
    public String toString() { return "Meal " + orderNum; }
}

class WaitPerson implements Runnable {
    private Restaurant restaurant;
    public WaitPerson(Restaurant restaurant) { this.restaurant = restaurant; }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized (this) { // wait as the lock, lock himself
                    while(restaurant.meal == null) {
                        wait(); // wait chef
                    }
                }
                System.out.println("waitperson got " + restaurant.meal);
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll(); // notify chef to be ready for another
                }
            }
        } catch (InterruptedException e) {
            System.out.println("waiter interrupted");
        }
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;
    private int count = 0;
    public Chef(Restaurant restaurant) { this.restaurant = restaurant; }
    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                synchronized (this) {
                    while(restaurant.meal != null) {
                        wait(); // no need to cook new
                    }
                }
                if(++count == 10) { // no more material
                    System.out.println("Out of food, closing");
                    restaurant.exec.shutdownNow(); // notify to close
                }
                System.out.println("Order up!");
                synchronized (restaurant.waiter) {
                    restaurant.meal = new Meal(count);
                    restaurant.waiter.notifyAll(); // notify the waiter that the new food is ready
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("chef interrupted");
        }
    }
}

public class Restaurant {
    Meal meal;
    WaitPerson waiter = new WaitPerson(this);
    Chef chef = new Chef(this);
    ExecutorService exec = Executors.newCachedThreadPool();
    public Restaurant() {
        exec.execute(chef);
        exec.execute(waiter);
    }
    public static void main(String[] args) {
        new Restaurant();
    }
}
