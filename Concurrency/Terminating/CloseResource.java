// one way to interrupt IO or sync is to close resource
package Terminating;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CloseResource {
    public static void main(String[] args) throws IOException, InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        ServerSocket server = new ServerSocket(8080);
        InputStream socketInput = new Socket("localhost", 8080).getInputStream();
        exec.execute(new IOBlocked(socketInput));
        exec.execute(new IOBlocked(System.in));
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Shutting down all threads");
        exec.shutdownNow();
        TimeUnit.MILLISECONDS.sleep(1000);
        System.out.println("Closing: " + socketInput.getClass().getName());
        socketInput.close(); // release blocked thread
        System.out.println("Closing: " + System.in.getClass().getName());
        System.in.close(); // release blocked thread
    }
}
