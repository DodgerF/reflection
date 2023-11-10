package com.example.reflection;

import com.example.reflection.annotation.ToRedact;
import com.example.reflection.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    private final Map<TextField, Field> fields = new HashMap();
    private Person person = new Person();
    private Label label = new Label();

    @FXML
    private VBox box;

    public HelloController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        box.getChildren().add(label);



        Class<?> clazz = person.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ToRedact.class)) {
                field.setAccessible(true);
                HBox hBox = new HBox();
                hBox.getChildren().add(new Label(field.getName()));
                TextField textField = new TextField();
                hBox.getChildren().add(textField);
                fields.put(textField, field);
                box.getChildren().add(hBox);

            }

        }

        System.out.println(fields.size());

    }

    @FXML
    public void click() throws IllegalAccessException{
        fields.forEach((tf, f) -> {
            System.out.println(f.getType());
            if (f.getType() == String.class) {
                try {
                    f.set(person, tf.getText());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }

            }

            else if (f.getType() == int.class) {
                try {
                    f.set(person, Integer.parseInt(tf.getText()));
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            else if (f.getType() == double.class) {
                try {
                    f.set(person, Double.parseDouble(tf.getText()));
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        update();
    }

    private void update() throws IllegalAccessException{
        var builder = new StringBuilder();
        Class<?> clazz = person.getClass();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            builder.append(declaredField.getName()).append(": ").append(declaredField.get(person)).append(";\n");
        }
        label.setText(builder.toString());
    }
}