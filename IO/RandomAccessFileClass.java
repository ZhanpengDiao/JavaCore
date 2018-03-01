// may potentially be substituted by nio

import java.io.*;

public class RandomAccessFileClass {
    static String file = "rtest.out";
    static void display() throws IOException {
        RandomAccessFile rf = new RandomAccessFile(file, "r");
        for(int i = 0; i < 10; i ++) {
            System.out.println(
                    "Value " + i + ": " + rf.readDouble()
            );
        }
        System.out.println(rf.readUTF());
        rf.close();
    }

    public static void main(String[] args) throws IOException{
        RandomAccessFile rf = new RandomAccessFile(file, "rw");
        for(int i = 0; i < 10; i ++) {
            rf.writeDouble(i + 1.0);
        }
        rf.writeUTF("end of file");
        rf.close();
        display();

        rf = new RandomAccessFile(file, "rw");
        rf.seek(8 * 5); // each eight bytes for one double
        rf.writeDouble(99.999);
        rf.close();
        display();
    }
}
