// different way of using Thread

import java.util.concurrent.*;

// using a named inner class
class InnerThread1 {
    private int countDown = 5;
    private Inner inner;
    private class Inner extends Thread {
        Inner(String name) {
            super(name);
            start();
        }
        public void run() {
            try {
                while(true) {
                    System.out.print(this);
                    if(--countDown == 0) return;
                    sleep(100);
                }
            } catch(InterruptedException e) {
                System.out.print("Interrupted");
            }
        }
        public String toString() {
            return getName() + ": " + countDown;
        }
    }

    public InnerThread1(String name) {
        inner = new Inner(name);
    }
}

// using anonymous inner class
class InnerThread2 {
    private int countDown = 5;
    private Thread inner;
    private class Inner extends Thread {
        Inner(String name) {
            super(name);
            start();
        }
        public void run() {
            try {
                while(true) {
                    System.out.print(this);
                    if(--countDown == 0) return;
                    sleep(100);
                }
            } catch(InterruptedException e) {
                System.out.print("Interrupted");
            }
        }
        public String toString() {
            return getName() + ": " + countDown;
        }
    }

    public InnerThread2(String name) {
        inner = new Thread(name) {
            public void run() {
                try {
                    while(true) {
                        System.out.print(this);
                        if(--countDown == 0) return;
                        sleep(100);
                    }
                } catch(InterruptedException e) {
                    System.out.print("Interrupted");
                }
            }
            public String toString() {
                return getName() + ": " + countDown;
            }
        };
        inner.start();
    }
}

// using a named Runnable
class InnerRunnable {
    private int countDown = 5;
    private Inner inner;
    private class Inner implements Runnable {
        Thread t;
        Inner(String name) {
            t = new Thread(this, name);
            t.start();
        }

        @Override
        public void run() {
            try {
                while(true) {
                    System.out.print(this);
                    if(--countDown == 0) return;
                    TimeUnit.MICROSECONDS.sleep(100);
                }
            } catch(InterruptedException e) {
                System.out.print("Interrupted");
            }
        }

        public String toString() {
            return t.getName() + ": " + countDown;
        }
    }

    InnerRunnable(String name) {
        inner = new Inner(name);
    }
}

// using anonymous Runnable
class InnerRunnable2 {
    private int countDown = 5;
    private Thread t;
    InnerRunnable2(String name) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        System.out.print(this);
                        if(--countDown == 0) return;
                        TimeUnit.MICROSECONDS.sleep(100);
                    }
                } catch(InterruptedException e) {
                    System.out.print("Interrupted");
                }
            }
            public String toString() {
                return t.getName() + ": " + countDown;
            }
        });
        t.start();
    }
}

// a separate method to run code as a task
class ThreadMethod {
    private int countDown = 5;
    private Thread t;
    private String name;
    public ThreadMethod(String name) { this.name = name; }
    public void runTask() {
        if(t == null) {
            t = new Thread(name) {
                public void run() {
                    try {
                        while(true) {
                            System.out.print(this);
                            if(--countDown == 0) return;
                            sleep(100);
                        }
                    } catch(InterruptedException e) {
                        System.out.print("Interrupted");
                    }
                }
                public String toString() {
                    return getName() + ": " + countDown;
                }
            };
            t.start();
        }
    }
}

public class ThreadVariations {
    public static void main(String[] args) {
        new InnerThread1("InnerThread1");
        new InnerThread2("InnerThread2");
        new InnerRunnable("InnerRunable");
        new InnerRunnable2("InnerRunnable2");
        new ThreadMethod("ThreadMethod").runTask();
    }
}
