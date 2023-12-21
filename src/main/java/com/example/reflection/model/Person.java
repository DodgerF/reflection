package com.example.reflection.model;


import com.example.reflection.annotation.ToRedact;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Person {
    @ToRedact
    private String name;
    @ToRedact
    private int age;
    @ToRedact
    private float weight;
    @ToRedact
    private char iChar;

    public Person() {
        name = "unknown";
        age = 0;
        weight = 0f;
        iChar = '0';
    }
}
