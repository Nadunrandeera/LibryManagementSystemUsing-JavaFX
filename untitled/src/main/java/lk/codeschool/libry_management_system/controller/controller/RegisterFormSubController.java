package lk.codeschool.libry_management_system.controller.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterFormSubController {
    public AnchorPane SignUpMainPane;//Anchor pane and button is a Node eken ekata adala full window ekama allanna puliwan

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        Stage window = (Stage) SignUpMainPane.getScene().getWindow();//window eke close option nathi nisa Stage ekkt cast kirima
        window.close();

        Stage stage = new Stage();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to the form").show();
            e.printStackTrace();
        }

    }
}
