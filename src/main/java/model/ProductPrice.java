package model;

public class ProductPrice {
    
    private float price;
    private String name;

    public ProductPrice(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}