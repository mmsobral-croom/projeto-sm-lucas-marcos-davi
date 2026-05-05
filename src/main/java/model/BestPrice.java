package model;

public class BestPrice {
    private String name;
    private float price;
    private String market;

    public BestPrice(String name, float price, String market) {
        this.name = name;
        this.price = price;
        this.market = market;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getMarket() {
        return market;
    }
}