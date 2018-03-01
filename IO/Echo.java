import java.io.*;

public class Echo {
    public static void main(String[] args) throws IOException{
        BufferedReader stdin = new BufferedReader(
                new InputStreamReader(System.in)); // System.in is a raw InputStream, should be wrapped before use
        String s;
        while((s = stdin.readLine()) != null && s.length() != 0) {
            System.out.println("Echo: " + s);
        }
    }
}
