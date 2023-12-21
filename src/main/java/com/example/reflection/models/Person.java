package com.example.reflection.models;


import com.example.reflection.ToRedact;

public class Person {
    @ToRedact
    private String name;
    @ToRedact
    private int age;
    @ToRedact
    private float weight;
    @ToRedact
    private char iChar;
    private double money;

    public Person() {
        name = "unknown";
        age = 0;
        weight = 0f;
        iChar = '0';
        money = 0.0;
    }
}
