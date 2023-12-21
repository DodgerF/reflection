package com.example.reflection.model;

import com.example.reflection.annotation.ToRedact;
import javafx.scene.control.Label;

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
