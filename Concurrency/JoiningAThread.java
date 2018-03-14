// understanding join()

class Sleeper extends Thread {
    private int duration;
    public Sleeper(String name, int sleepTime) {
        super(name);
        duration = sleepTime;
        this.start();
    }

    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
           System.out.println(getName() + " was interrupted. " +
           "isInterrupted(): " + isInterrupted());
           return;
        }
        System.out.println(getName() + " has awakened.");
    }
}

class Joiner extends Thread {
    private Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        this.start();
    }

    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.println("Interrputed");
        }
        System.out.println(getName() + " join completed");
    }
}

public class JoiningAThread {
    public static void main(String[] args) {
        Sleeper sleepy = new Sleeper("sleepy", 2000);
        Sleeper grumpy = new Sleeper("grumpy", 2000);
        Joiner dopey = new Joiner("doepy", sleepy);
        Joiner doc = new Joiner("doc", sleepy);
        grumpy.interrupt();
    }
}
