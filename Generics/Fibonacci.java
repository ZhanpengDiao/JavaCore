// Fibonacci with generator

import Generator.Generator;

public class Fibonacci implements Generator<Integer> {
    private int count = 0;
    private int fib(int i) {
        if(i < 2) return 1;
        else return fib(i - 1) + fib(i - 2);
    }
    @Override
    public Integer next() {
        return fib(count++);
    }

    public static void main(String[] args) {
        Fibonacci fi = new Fibonacci();
        for(int i = 0; i < 10; i ++) {
            System.out.println(fi.next());
        }
    }
}
