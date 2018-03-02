package NIO;

// nio is closer to the way that system operates I/O
// channels and buffers, increase speed
import java.nio.*;
import java.nio.channels.*;
import java.io.*;

public class ChannelCopy {
    private static final int BSIZE = 1024; // for read-only access, buffer size must be explicitly allocated
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: sourcefile destfile");
            System.exit(1);
        }

        FileChannel in = new FileInputStream(args[0]).getChannel();
        FileChannel out = new FileInputStream(args[1]).getChannel();
        ByteBuffer bf = ByteBuffer.allocate(BSIZE);

        while(in.read(bf) != 1) {
            bf.flip(); // prepare for writing
            out.write(bf);
            bf.clear(); // prepare for reading
        }
    }
}
