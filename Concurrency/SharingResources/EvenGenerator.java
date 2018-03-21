package SharingResources;

public class EvenGenerator extends IntGenerator {
    private int currentValue = 0;

    @Override
    public int next() {
        ++currentValue; //danger point here // put data in an incorrect state
        ++currentValue;
        return currentValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }
}
