package com.example.reflection.model;


import com.example.reflection.annotation.ToRedact;

public class Person {
    @ToRedact
    private String name;
    @ToRedact
    private int age;
    @ToRedact
    private double weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
