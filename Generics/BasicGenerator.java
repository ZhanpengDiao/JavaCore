class CountedObject {
    private static int counter = 0;
    private final int id = counter++;
    public String toString() {
        return "CountedObject " + id;
    }
}

public class BasicGenerator implements Generator {
    private Class type;
    public BasicGenerator(Class type) { this.type = type; }

    @Override
    public Object next() {
        try {
            return type.newInstance();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Generator create(Class type) {
        return new BasicGenerator(type);
    }

    public static void main(String[] args) {
        Generator gen = BasicGenerator.create(CountedObject.class);
        for(int i = 0; i < 5; i ++) {
            System.out.println(gen.next());
        }
    }
}
