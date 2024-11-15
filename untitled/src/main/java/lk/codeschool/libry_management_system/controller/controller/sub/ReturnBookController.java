package lk.codeschool.libry_management_system.controller.controller.sub;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class ReturnBookController {
    public TextField txtSearchKeyword;
    public RadioButton rbBookID;
    public ToggleGroup txt;
    public RadioButton rbMemberID;
    public RadioButton rbMobileNumber;
    public TableView tblNotReturnBookRecord;
    public TableColumn colRecordID;
    public TableColumn colBookID;
    public TableColumn colBookName;
    public TableColumn colMemberID;
    public TableColumn colMemberName;
    public TableColumn colReturnDate;
    public Label LateDateCount;
    public TextField txtFindForOneDay;
    public Label lblFine;
    public TextField txtPayment;
    public Label lblBalance;

    public void btnMarkAsReturnOnAction(ActionEvent actionEvent) {
    }
}
