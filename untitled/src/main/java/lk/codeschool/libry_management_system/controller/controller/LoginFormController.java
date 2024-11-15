package lk.codeschool.libry_management_system.controller.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public AnchorPane mainSubAnchorpane;
    public AnchorPane subAnchorpane1;
    public AnchorPane subAnchorpane2;
    public TextField txtUserName;
    public TextField txtPassword;
    public Hyperlink lblForgotPassword;
    public PasswordField pwfPassword;

    public void btnViewAndHideOnAction(ActionEvent actionEvent) {
    }

    public void btnLoginOnAction(ActionEvent actionEvent) {
        Stage window = (Stage) txtPassword.getScene().getWindow();
        window.close();
        Stage stage = new Stage();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/main_dashboard_form.fxml"));
            Scene scene = new Scene(load);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to the form").show();
            e.printStackTrace();
        }
    }

    public void btnCreateANAccountOnAction(ActionEvent actionEvent) {
        subAnchorpane2.getChildren().clear(); //clear form
        try {
            Node load = FXMLLoader.load(getClass().getResource("/view/register_formSub.fxml")); //(ui object)load the register form
            ObservableList<Node> children = subAnchorpane2.getChildren();
            children.add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to the form").show();
            e.printStackTrace();
        }
    }
}
