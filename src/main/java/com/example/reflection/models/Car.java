package com.example.reflection.models;

import com.example.reflection.ToRedact;

public class Car {
    @ToRedact
    private String name;
    @ToRedact
    private boolean isForeignCar;
    @ToRedact
    private double price;

    public Car() {
        name = "unknown";
        isForeignCar = false;
        price = 0.0;
    }
}
