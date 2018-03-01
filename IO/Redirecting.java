// Redirecting standard IO
// Redirecting the standard IO to files

import java.io.*;

public class Redirecting {
    public static void main(String[] args) throws Exception{
        PrintStream console = System.out; // the current std output stream

        BufferedInputStream in = new BufferedInputStream(new FileInputStream("./IO/Redirecting.java"));
        PrintStream out = new PrintStream(new FileOutputStream("Redirecting.out"));

        System.setIn(in);
        System.setOut(out);
        System.setOut(out);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s;
        while((s = br.readLine()) != null) {
            System.out.println(s); // redirect to the output file
        }
        out.close();
        System.setOut(console);
    }
}
