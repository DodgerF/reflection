package com.example.reflection.controllers;

import com.example.reflection.ToRedact;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.example.reflection.controllers.CharDecoder.getChar;

public class ModalStageController {
    private Object obj;
    private MenuController controller;

    @FXML
    private VBox box;

    public void setObject(Object object) {
        obj = object;

        box.setAlignment(Pos.CENTER);

        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ToRedact.class)) {
                field.setAccessible(true);

                HBox hBox = new HBox();
                hBox.getChildren().add(addText(field.getName()));

                Control control;
                if (field.getType() == boolean.class) {
                    control = createCheckBox(field);
                    try {
                        ((CheckBox) control).setSelected((boolean) (field.get(obj)));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    control = createTextField(field);
                    try {
                        ((TextField) control).setText(String.valueOf(field.get(obj)));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

                hBox.getChildren().add(control);
                box.getChildren().add(hBox);
            }
        }
    }
    public void setController(MenuController c){
        controller = c;
    }

    private TextField addText(String string) {
        TextField textField = new TextField(string);
        textField.setDisable(true);
        textField.setAlignment(Pos.CENTER);
        double MAX_TEXT_WIDTH = 100;
        textField.setMaxWidth(MAX_TEXT_WIDTH);
        return textField;
    }

    private TextField createTextField(Field field) {

        TextField textField = new TextField();
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            textField.setStyle("-fx-text-fill: #000000;");
            if (!newValue) {
                setFieldValue(field, textField);
                try {
                    controller.updateLabel(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return textField;
    }
    private CheckBox createCheckBox(Field field) {
        CheckBox checkBox = new CheckBox();
        checkBox.setOnMouseClicked(event -> {
            setFieldValue(field, checkBox);
            try {
                controller.updateLabel(obj);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return checkBox;
    }

    private void setMistake(TextField textField) {
        textField.setStyle("-fx-text-fill: #B22222;");
    }

    private void setFieldValue(Field field, Control control) {
        String value = null;

        if (control.getClass().equals(TextField.class)) {
            control.setStyle("-fx-text-fill: #000000;");
            value = ((TextField) control).getText();
        }

        if (field.getType() == String.class) {
            try {
                if (value.isEmpty()){
                    setMistake((TextField)control);
                }
                else {
                    field.set(obj, ((TextField) control).getText());
                }
            }
            catch (Exception ignored) {
            }

        }
        else if (field.getType() == char.class){
            try {
                field.set(obj, getChar(value));
            }
            catch (Exception ignored) {
                setMistake((TextField)control);
            }
        }

        else if (field.getType() == int.class) {
            try {
                field.set(obj, Integer.parseInt(value));
            }
            catch (Exception ignored) {
                setMistake((TextField)control);
            }
        }
        else if (field.getType() == float.class) {
            try {
                field.set(obj, Float.valueOf(value));
            }
            catch (Exception ignored) {
                setMistake((TextField)control);
            }
        }

        else if (field.getType() == double.class) {
            try {
                field.set(obj, Double.parseDouble(value));
            }
            catch (Exception ignored) {
                setMistake((TextField)control);
            }
        }
        else if (field.getType() == boolean.class){
            try {
                field.set(obj, ((CheckBox)control).isSelected());
            }
            catch (Exception ignored) {
            }
        }
    }

}