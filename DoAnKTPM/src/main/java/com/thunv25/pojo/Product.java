package com.thunv25.pojo;

import com.thunv25.utils.Utils;

public class Product {

    private String id;
    private String name;
    private String origin;
    private double price;
    private double promoPrice;
    private double unit;

    public Product() {
//        this.id = 0;
//        this.name = "";
//        this.origin = "";
//        this.price = 0;
//        this.unit = "";
    }

    public Product(String id, String name, String origin, double price) {
        this.id = id;
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.unit = 1;
        this.promoPrice = 1;
    }
    
    public Product(String name, String origin, double price) {
        this.id = Utils.getUUID();
        this.name = name;
        this.origin = origin;
        this.price = price;
        this.unit = 1;
        this.promoPrice = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }
    
    public void setPromoPrice(double promoPrice) {
        this.promoPrice = promoPrice;
    }
    
    public double getPromoPrice() {
        return promoPrice;
    }
    @Override
    public String toString() {
        return this.name;
    }

}
