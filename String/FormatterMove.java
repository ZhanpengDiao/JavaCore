import java.io.*;
import java.util.*;

public class FormatterMove {
    private String name;
    private Formatter f;

    public FormatterMove(String name, Formatter f) {
       this.name = name;
       this.f = f;
    }

    public void move(int x, int y) {
        f.format("%s This guy is at (%d, %d).\n", name, x, y);
    }

    public static void main(String[] args) {
        FormatterMove bob = new FormatterMove("Bob", new Formatter(System.out));
        FormatterMove tom = new FormatterMove("Tom", new Formatter(System.out));
        bob.move(2,3);
        tom.move(3, 4);
        bob.move(3,4);
        tom.move(5,6);
    }
}
