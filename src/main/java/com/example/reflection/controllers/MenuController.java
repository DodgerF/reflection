package com.example.reflection.controllers;

import com.example.reflection.Main;
import com.example.reflection.models.Car;
import com.example.reflection.models.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {
    @FXML
    private Label label;
    private final Car car;
    private final Person person;
    private Object object;

    public MenuController() {
        car = new Car();
        person = new Person();
        object = person;
    }

    public void personButtonClicked() throws IllegalAccessException {
        updateLabel(person);
        object = person;
    }
    public void carButtonClicked() throws IllegalAccessException {
        updateLabel(car);
        object = car;
    }

    public void createStage() {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("modal-view.fxml"));

        Scene statisticScene = null;
        try{
            statisticScene = new Scene(loader.load(), 250, 150);
        }
        catch (Exception ignore){
        }
        Stage statisticStage = new Stage();
        statisticStage.initModality(Modality.APPLICATION_MODAL);
        statisticStage.setScene(statisticScene);
        statisticStage.setResizable(false);
        statisticStage.show();

        ((ModalStageController)loader.getController()).setObject(object);
        ((ModalStageController)loader.getController()).setController(this);
    }

    public void updateLabel(Object object) throws IllegalAccessException {
        var builder = new StringBuilder();
        Class<?> clazz = object.getClass();
        builder.append(object.getClass().getSimpleName()).append(":\n\n");
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            builder.append(declaredField.getName()).append(": ").append(declaredField.get(object)).append("\n");
        }
        label.setText(builder.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updateLabel(person);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
