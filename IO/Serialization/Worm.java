// demo of serialization
// provide lightweight persistence

package Serialization;

import java.io.*;
import java.util.*;

class Data implements Serializable {
    private int n;
    public Data(int n) { this.n = n; }
    public String toString() {
        return Integer.toString(n);
    }
}

public class Worm implements Serializable {
    private Random rand = new Random();
    private Data[] da = {
            new Data(rand.nextInt(10)),
            new Data(rand.nextInt(10)),
            new Data(rand.nextInt(10)),
    };
    private Worm next;
    private char c;
    public Worm() {
        System.out.println("Default constructor");
    }
    // Value of i == number of segments
    public Worm(int i, char c) {
        System.out.println("Worm constructor: " + i);
        this.c = c;
        if(--i > 0) {
            next = new Worm(i, (char)(c + 1));
        }
    }
    public String toString() {
        StringBuilder sb = new StringBuilder(":");
        sb.append(c);
        sb.append("(");
        for(Data d: da) {
            sb.append(d);
        }
        sb.append(")");
        if(next != null) {
            sb.append(next);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Worm w = new Worm(6, 'a');
        System.out.println("w = " + w);

        // send data out for storage
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("./Worm.out"));
        out.writeObject("Worm storage\n");
        out.writeObject(w); // cannot write if not serializable
        out.close();

        // read data in
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("./Worm.out"));
        String s = (String)in.readObject();
        Worm inWorm = (Worm)in.readObject();
        System.out.print(s);
        System.out.println("Worm-in = " + inWorm);

        // send data out as Byte in memory
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out2 = new ObjectOutputStream(bout);
        out2.writeObject("Worm storage\n");
        out2.writeObject(w);
        out2.flush();

        ObjectInputStream in2 = new ObjectInputStream(
                new ByteArrayInputStream(bout.toByteArray()));
        String s2 = (String)in2.readObject();
        Worm inWorm2 = (Worm)in2.readObject();
        System.out.print(s2);
        System.out.println("Worm-in = " + inWorm2);

    }
}
