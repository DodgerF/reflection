package com.example.reflection;

import com.example.reflection.model.Car;
import com.example.reflection.model.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class MenuController {
    @FXML
    private Button personButton;
    @FXML
    private Button carButton;
    @FXML
    private Label personLabel;
    @FXML
    private Label carLabel;

    private Car car = new Car();
    private Person person = new Person();

    public void personButtonClicked() {
        createStage(person);
    }
    public void carButtonClicked() {
        createStage(car);
    }
    private void createStage(Object object) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

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
        statisticStage.setTitle("Statistics");
        statisticStage.setResizable(false);
        statisticStage.show();
        ((HelloController)loader.getController()).setObject(object);
    }

}
