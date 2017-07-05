package hello;

import org.springframework.data.annotation.Id;


public class Product {

    @Id
    public String id;

    public String name;
    public double price;

    public Product() {}

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(
                "Product[id=%s, name='%s', price='%s']",
                id, name, price);
    }

}

