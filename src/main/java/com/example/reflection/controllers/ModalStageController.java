package com.example.reflection.controllers;

import com.example.reflection.ToRedact;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class ModalStageController implements Initializable {
    private final Map<Control, Field> fields = new HashMap();
    private Object obj;
    private MenuController controller;

    @FXML
    private VBox box;
    @FXML
    private Text text;

    private boolean isMistake;

    @FXML
    public void click() throws IllegalAccessException {
        refresh();
        getValuesFromFields();
        controller.updateLabel(obj);
        close();
    }
    public void setObject(Object object) {
        obj = object;

        Class<?> clazz = obj.getClass();
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
                hBox.getChildren().add(control);

                fields.put(control, field);
                box.getChildren().add(hBox);
            }
        }
    }
    public void setController(MenuController c){
        controller = c;
    }
    private void close(){
        if (isMistake) {
            text.setVisible(true);
            return;
        }
        Stage stage = (Stage) text.getScene().getWindow();
        stage.close();
    }

    private void refresh() {
        isMistake = false;
        text.setVisible(false);
    }
    private void setMistake(TextField textField) {
        textField.setStyle("-fx-text-fill: #B22222;");
        isMistake = true;
    }

    private void getValuesFromFields() {
        fields.forEach((control, f) -> {
            control.setStyle("-fx-text-fill: #000000;");
            if (f.getType() == String.class) {
                try {
                    if (((TextField)control).getText().isEmpty()){
                        setMistake((TextField)control);
                    }
                    else {
                        f.set(obj, ((TextField) control).getText());
                    }
                }
                catch (Exception ignored) {
                }

            }
            else if (f.getType() == char.class){
                try {
                    f.set(obj, getChar(((TextField)control).getText()));
                }
                catch (Exception ignored) {
                    setMistake((TextField)control);
                }
            }

            else if (f.getType() == int.class) {
                try {
                    f.set(obj, Integer.parseInt(((TextField)control).getText()));
                }
                catch (Exception ignored) {
                    setMistake((TextField)control);
                }
            }
            else if (f.getType() == float.class) {
                try {
                    f.set(obj, Float.valueOf(((TextField)control).getText()));
                }
                catch (Exception ignored) {
                    setMistake((TextField)control);
                }
            }

            else if (f.getType() == double.class) {
                try {
                    f.set(obj, Double.parseDouble(((TextField)control).getText()));
                }
                catch (Exception ignored) {
                    setMistake((TextField)control);
                }
            }
            else if (f.getType() == boolean.class){
                try {
                    f.set(obj, ((CheckBox)control).isSelected());
                }
                catch (Exception ignored) {
                }
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();

    }
}