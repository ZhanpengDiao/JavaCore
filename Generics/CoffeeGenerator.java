import java.util.*;

class Coffee {
    private static int counter = 0;
    private final int id = counter++;
    public String toString() {
        return getClass().getSimpleName() + " " + id;
    }
}

class Latte extends Coffee {}

class Mocha extends Coffee {}

class Cappuccino extends Coffee {}

class Americano extends Coffee {}

class Breve extends Coffee {}

public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {
    private Class[] types = {
            Latte.class, Mocha.class, Cappuccino.class, Americano.class,Breve.class};
    private Random rand = new Random();
    private int size = 0;

    public CoffeeGenerator() {}
    public CoffeeGenerator(int size) { this.size = size; }

    @Override
    public Coffee next() {
       try {
           return (Coffee)types[rand.nextInt(types.length)].newInstance();
       } catch(Exception e) {
           throw new RuntimeException(e);
       }
    }

    public Iterator<Coffee> iterator() {
        return new Iterator<Coffee>() {
            int count = size;
            @Override
            public boolean hasNext() { return count > 0; }

            @Override
            public Coffee next() {
                count--;
                return CoffeeGenerator.this.next();
            }

            @Override
            public void remove() {} // not implemented
        };
    }

    public static void main(String[] args) {
        CoffeeGenerator cg = new CoffeeGenerator();

        for(int i = 0; i < 5; i ++) {
            System.out.println(cg.next());
        }

        for(Coffee c: new CoffeeGenerator(5)) {
            System.out.println(c);
        }
    }
}
