// pretend the entire file is in memory
// can get access like an array

package NIO;

import java.io.RandomAccessFile;
import java.nio.*;
import java.nio.channels.FileChannel;

public class LargeMappedFile{
    static int LEN = 4096;
    public static void main(String[] args) throws Exception{
        MappedByteBuffer out = new RandomAccessFile("./LargeMappedFile.out", "rw").getChannel()
                .map(FileChannel.MapMode.READ_WRITE, 0, LEN);
        for(int i = 0; i < LEN; i ++) {
            out.put((byte)'X');
        }
        System.out.println("Finish writing");
        for(int i = LEN / 2; i < LEN / 2 + 6; i ++) {
            System.out.print((char)out.get(i));
        }
    }
}
