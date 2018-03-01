import java.io.*;

public class BasicFileOutput {
    public static String file = "BasicFileOutput.out";
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("./IO/BasicFileOutput.java"));
        PrintWriter out = new PrintWriter(file);

        String s;
        int lineCount = 0;
        while((s = in.readLine()) != null) {
            out.format("%-5d%s\n", ++lineCount, s);
        }
        out.close();
    }
}
