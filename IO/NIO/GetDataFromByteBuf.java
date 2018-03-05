package NIO;

import java.nio.*;

public class GetDataFromByteBuf {
    private static final int BSIZE = 1024;
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        //store & read char array
        bb.asCharBuffer().put("Good!");
        char c;
        while((c = bb.getChar()) != 0) {
            System.out.print(c + " ");
        }
        bb.rewind();
        //store & read int
        bb.asIntBuffer().put(12345);
        System.out.print(bb.getInt());
    }
}
