import java.util.*;

public class Generators {
    public static <T> void fill(ArrayList<T> container, Generator<T> generator, int nProducts) {
        for(int i = 0; i < nProducts; i ++) {
            container.add(generator.next());
        }
    }
}
