package NIO;

// data converting
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.io.*;

public class BufferToText {
    private static final int BSIZE = 1024;
    public static void main(String[] args) throws Exception {

        // the first part shows a wrong solution
        FileChannel fc = new FileOutputStream("BufToTxt.out").getChannel();
        fc.write(ByteBuffer.wrap("some text".getBytes()));
        fc.close();
        fc = new FileInputStream("BufToTxt.out").getChannel();
        ByteBuffer bf = ByteBuffer.allocate(BSIZE);
        fc.read(bf);
        bf.flip();
        System.out.println(bf.asCharBuffer());

        // 1.decoding using the default Charset
        bf.rewind(); // re-reading data
        String encoding = System.getProperty("file.encoding");
        System.out.println("Decoded using " + encoding);
        System.out.println(Charset.forName(encoding).decode(bf));

        // 2.encode with something that will print
        fc = new FileOutputStream("BufToTxt.out").getChannel();
        fc.write(ByteBuffer.wrap("some more text".getBytes("UTF-16BE")));
        fc.close();
        fc = new FileInputStream("BufToTxt.out").getChannel();
        bf.clear();
        fc.read(bf);
        bf.flip();
        System.out.println(bf.asCharBuffer());

        // directly use Charbuffer to write through
        fc = new FileOutputStream("BufToTxt.out").getChannel();
        bf = ByteBuffer.allocate(64);
        bf.asCharBuffer().put("text by using CharBuffer");
        fc.write(bf);
        fc.close();
        fc = new FileInputStream("BufToTxt.out").getChannel();
        bf.clear();
        fc.read(bf);
        bf.flip();
        System.out.println(bf.asCharBuffer());
    }
}
