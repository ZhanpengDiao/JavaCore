// ByteBuffer uses big endian by default

package NIO;

import java.util.*;
import java.nio.*;

public class Endianness {
    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        bb.asCharBuffer().put("abcdef");
        System.out.println(Arrays.toString(bb.array()));

        bb.order(ByteOrder.BIG_ENDIAN);
        bb.asCharBuffer().put("abcdef"); // have to refill the data
        System.out.println(Arrays.toString(bb.array()));

        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.asCharBuffer().put("abcdef"); // have to refill the data
        System.out.println(Arrays.toString(bb.array()));
    }
}
