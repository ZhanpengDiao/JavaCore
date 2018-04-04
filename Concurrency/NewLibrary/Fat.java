// expensive to create
// we will then use Pool to limit the impact of constructor

package NewLibrary;

public class Fat {
    private volatile double d; // prevent optimization
    private static int counter = 0;
    private final int id = counter ++;

    public Fat() {
        // expensive
        for(int i = 1; i < 10000; i ++) {
            d += (Math.PI + Math.E) / i;
        }
    }

    public void operate() { System.out.println(this); }

    @Override
    public String toString() {
        return "Fat " + id;
    }
}
