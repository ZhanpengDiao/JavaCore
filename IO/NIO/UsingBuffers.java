package NIO;

import java.nio.*;

public class UsingBuffers {
    private static void symmetricScramble(CharBuffer cbf) {
       while(cbf.hasRemaining()) {
           cbf.mark();
           char c1 = cbf.get();
           char c2 = cbf.get();
           cbf.reset();
           cbf.put(c2).put(c1);
       }
    }

    public static void main(String[] args) {
        String data = "using buffers!";

//        ByteBuffer bb = ByteBuffer.allocate(data.length());
//        CharBuffer cbf2 = bb.asCharBuffer();
//        cbf2.put(data.toCharArray());

        CharBuffer cbf = CharBuffer.wrap(data.toCharArray());
        System.out.println(cbf.rewind());
        symmetricScramble(cbf);
        System.out.println(cbf.rewind());
        symmetricScramble(cbf);
        System.out.println(cbf.rewind());
    }
}
