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
    private double weight;
    @FXML
    private Label label;

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

    public void setLabel(Label label) {
        this.label = label;
        System.out.println(label);
    }


    public void changed() {

        label.setText("name: " + name + "\n" + "age: " + age + "\n" + "weight: " + weight + "\n");
    }
}
