class CountedObject {
    private static int counter = 0;
    private final int id = counter++;
    public String toString() {
        return "CountedObject " + id;
    }
}

public class BasicGenerator<T> implements Generator<T> {
    private Class<T> type;
    public BasicGenerator(Class<T> type) { this.type = type; }

    @Override
    public T next() {
        try {
            return type.newInstance();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Generator<T> create(Class<T> type) {
        return new BasicGenerator<T>(type);
    }

    public static void main(String[] args) {
        Generator<CountedObject> gen = BasicGenerator.create(CountedObject.class);
        for(int i = 0; i < 5; i ++) {
            System.out.println(gen.next());
        }
    }
}
