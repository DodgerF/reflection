package com.example.reflection.model;

import com.example.reflection.annotation.ToRedact;

public class Car {
    @ToRedact
    private String name;
    @ToRedact
    private boolean isForeignCar;
    private double price;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isForeignCar() {
        return isForeignCar;
    }
    public void setForeignCar(boolean foreignCar) {
        isForeignCar = foreignCar;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
