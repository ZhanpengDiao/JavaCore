import java.io.*;

public class FormattedMemoryInput {
    public static void main(String[] args) throws IOException{
        DataInputStream in = new DataInputStream( // byte-oriented I/O
                new BufferedInputStream(
                new FileInputStream("./IO/FormattedMemoryInput.java")));

        while(in.available() != 0) { // available() is inherited from FilterInputSteam
                System.out.print((char)in.readByte());
        }
        in.close();
    }
}
