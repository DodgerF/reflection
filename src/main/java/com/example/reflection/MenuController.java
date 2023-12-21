package com.example.reflection;

import com.example.reflection.model.Car;
import com.example.reflection.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {
    @FXML
    private Label personLabel;
    @FXML
    private Label carLabel;

    private Car car;
    private Person person;

    public MenuController() {
        car = new Car();
        person = new Person();
    }

    public void personButtonClicked() {
        createStage(person);
    }
    public void carButtonClicked() {
        createStage(car);
    }

    private void createStage(Object object) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));

        Scene statisticScene = null;
        try{
            statisticScene = new Scene(loader.load(), 300, 400);
        }
        catch (Exception ignored){
            System.out.println(ignored.getMessage());
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
        builder.append(object.getClass().getSimpleName()).append(":\n");
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            builder.append(declaredField.getName()).append(": ").append(declaredField.get(object)).append("\n");
        }
        if (clazz == car.getClass())
            carLabel.setText(builder.toString());
        if (clazz == person.getClass())
            personLabel.setText(builder.toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            updateLabel(person);
            updateLabel(car);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
