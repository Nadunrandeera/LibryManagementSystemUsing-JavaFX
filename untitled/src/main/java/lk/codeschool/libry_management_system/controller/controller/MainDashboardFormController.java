package lk.codeschool.libry_management_system.controller.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainDashboardFormController {
    public AnchorPane mainButtonPane;
    public AnchorPane mainPane;

    public void btnManageBooksOnAction(ActionEvent actionEvent) {
        loadUI("manage_book_form.fxml");
    }

    public void btnManageMembersOnAction(ActionEvent actionEvent) {
        loadUI("manage_member_form.fxml");
    }
    
    public void btnManageAuthorsandPublisherOnAction(ActionEvent actionEvent) {
        loadUI("manage_authors_and_publishers_form.fxml");
    }

    public void btnBorrowBookRecordsOnAction(ActionEvent actionEvent) {
        loadUI("borrow_book_form.fxml");
    }

    public void btnReturnBookRecordOnAction(ActionEvent actionEvent) {
        loadUI("return_book_form.fxml");
    }
    public void loadUI(String uiName){
        mainPane.getChildren().clear();
        try {
            Parent load = FXMLLoader.load(getClass().getResource("/view/sub/"+uiName));
            mainPane.getChildren().add(load);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR,"Failed to the form").show();
            e.printStackTrace();
        }
    }
}
