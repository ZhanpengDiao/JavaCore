// Building up a complex model using generic containers
// a retail store with aisles, shelves and products

import java.util.*;

class Product{
    private final int id;
    private String description;
    private double price;
    public Product(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
        System.out.println(toString());
    }

    public String toString() {
        return id + ": " + description + ", price: $" + price;
    }

    public void priceChange(double change) {
        price += change;
    }

    public static Generator<Product> generator() {
        return new Generator<Product>() {
            private Random rand = new Random();
            @Override
            public Product next() {
                return new Product(rand.nextInt(1000), "TestProduct",
                        Math.round(rand.nextDouble() * 1000) + 0.99);
            }
        };
    }
}

class Shelf extends ArrayList<Product> {
    public Shelf(int nProducts) {
        Generators.fill(this, Product.generator(), nProducts);
    }
}

class Aisle extends ArrayList<Shelf> {
    public Aisle(int nShelves, int nProducts) {
        for(int i = 0; i < nShelves; i++) {
            this.add(new Shelf(nProducts));
        }
    }
}

public class Store extends ArrayList<Aisle> {
    public Store(int nAisles, int nShelves, int nProducts) {
        for(int i = 0; i < nAisles; i ++) {
            this.add(new Aisle(nShelves, nProducts));
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Aisle a: this) {
            for(Shelf s: a) {
                for(Product p: s) {
                    sb.append(p);
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Store(2, 3, 4));
    }
}
