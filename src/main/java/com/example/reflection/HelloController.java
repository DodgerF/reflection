package com.example.reflection;

import com.example.reflection.annotation.ToRedact;
import com.example.reflection.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HelloController {
    private final Map<Control, Field> fields = new HashMap();
    private Object _object;
    @FXML
    private VBox box;
    @FXML
    private Label label;
    public void setObject(Object object) {
        _object = object;

        Class<?> clazz = _object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ToRedact.class)) {
                field.setAccessible(true);
                HBox hBox = new HBox();
                hBox.getChildren().add(new Label("  " + field.getName() + "    "));
                Control control;
                if (field.getType() == boolean.class)
                {
                    control = new CheckBox();
                }
                else {
                    control = new TextField();
                }
                System.out.println(control.getClass());
                hBox.getChildren().add(control);
                fields.put(control, field);
                box.getChildren().add(hBox);
            }
        }
    }


    @FXML
    public void click() throws IllegalAccessException{
        label.setText("");
        fields.forEach((control, f) -> {
            System.out.println(f.getType());
            if (f.getType() == String.class) {
                try {
                    f.set(_object, ((TextField)control).getText());
                }
                catch (Exception exception) {
                    label.setText(" " + f.getName() + " значение должно быть строковое" + "\n");
                    exception.printStackTrace();
                }

            }

            else if (f.getType() == int.class) {
                try {
                    f.set(_object, Integer.parseInt(((TextField)control).getText()));
                }
                catch (Exception exception) {
                    label.setText(label.getText() + " " + f.getName() + " значение должно быть целочисленное" + "\n");
                }
            }

            else if (f.getType() == double.class) {
                try {
                    f.set(_object, Double.parseDouble(((TextField)control).getText()));
                }
                catch (Exception exception) {
                    label.setText(label.getText() + " " + f.getName() + " значение должно быть типа double" + "\n");
                }
            }
            else if (f.getType() == boolean.class){
                try {
                    f.set(_object, ((CheckBox)control).isSelected());
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
        Class<?> clazz = _object.getClass();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            builder.append(declaredField.getName()).append(": ").append(declaredField.get(_object)).append(";\n");
        }
        ((Person)_object).changed();
    }
}