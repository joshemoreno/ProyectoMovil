package com.example.marketplace;

public class Product {

    private String name;
    private String description;
    private String price;
    private String quantity;
    private String url;

    public Product(String name, String description, String price, String quantity, String url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.url = url;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUrl() {
        return url;
    }
}
