package com.example.marketplace.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {

    private String name;
    private String description;
    private String price;
    private String quantity;
    private String url;
    private boolean favorite;
    private String count;
    private String id;
    private String cantIni;


    public Product(String name, String description, String price, String quantity, String url, String id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.url = url;
        favorite = false;
        this.id = id;
    }

    public Product(String name, String price, String count, String cantIni) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.cantIni = cantIni;
    }

    public Product() {
        favorite = false;
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

    public boolean isFavorite() {
        return favorite;
    }

    public String getCount() {
        return count;
    }

    public String getCantIni() {
        return cantIni;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
