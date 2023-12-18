package com.example.reflection.model;

import com.example.reflection.annotation.ToRedact;

public class Car {
    @ToRedact
    private boolean isForeignCar;
    @ToRedact
    private String name;
    private double price;

    public boolean isForeignCar() {
        return isForeignCar;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setForeignCar(boolean foreignCar) {
        isForeignCar = foreignCar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
