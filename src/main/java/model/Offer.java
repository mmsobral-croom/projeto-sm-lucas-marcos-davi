package model;

public class Offer {
    private String name;
    private String size;
    private float price;
    private String market;
    private float pricePerKg;

    public Offer(String name, String size, float price, String market, float pricePerKg) {
        this.name = name;
        this.size = size;
        this.price = price;
        this.market = market;
        this.pricePerKg = pricePerKg;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public float getPrice() {
        return price;
    }

    public String getMarket() {
        return market;
    }

    public float getPricePerKg() {
        return pricePerKg;
    }
}