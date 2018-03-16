// can't directly catch the exception escaped from a thread
import java.util.concurrent.*;

class ExceptionThread implements Runnable {
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t);
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Caught " + e);
        System.exit(1);
    }
}

class HandlerThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " is creating a new thread.");
        Thread t = new Thread(r);
        System.out.println(t + " is created.");
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler()); // attach a exception handler to a thread obj
        System.out.println( "eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}

public class HandleUncaughtException {
    public static void main(String[] args) {
        ExecutorService exe = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exe.execute(new ExceptionThread());

        // can also set default uncaught exception handler
//        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }
}
