import java.io.*;

public class BufferedInputFile {
    public static String read(String filename) throws IOException {
        // read file by lines
        BufferedReader bf = new BufferedReader(new FileReader(filename));
        String s;
        StringBuffer sb = new StringBuffer();
        while((s = bf.readLine()) != null) {
            sb.append(s + "\n");
        }
        bf.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException{
        System.out.println(read("./IO/BufferedInputFile.java"));
    }
}