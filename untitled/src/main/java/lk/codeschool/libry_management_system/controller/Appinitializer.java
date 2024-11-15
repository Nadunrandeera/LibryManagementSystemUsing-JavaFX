package lk.codeschool.libry_management_system.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Appinitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
            Scene scene = new Scene(load);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to load login form - Contact Developer").show();
            e.printStackTrace();
        }
    }
}
