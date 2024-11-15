package lk.codeschool.libry_management_system.controller.controller.sub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.codeschool.libry_management_system.controller.dto.custom.MemberDTO;
import lk.codeschool.libry_management_system.controller.repo.custom.MemberRepo;
import lk.codeschool.libry_management_system.controller.repo.util.RepoFactory;
import lk.codeschool.libry_management_system.controller.repo.util.RepoTypes;
import lk.codeschool.libry_management_system.controller.service.custom.impl.MemberServiceIMPL;
import lk.codeschool.libry_management_system.controller.service.util.ServiceFactory;
import lk.codeschool.libry_management_system.controller.service.util.ServiceTypes;
import lk.codeschool.libry_management_system.controller.tm.MemberTM;
import lk.codeschool.libry_management_system.controller.util.exception.custom.MemberException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageMemberFormController {

    public TextField txtMemberID;
    public TextField txtMemberName;
    public TextField txtMemberAddress;
    public TextField txtMemberEmail;
    public TextField txtMemberContact;
    public TableView<MemberTM> tblMember;
    public TableColumn<MemberTM,String> colMemberID;
    public TableColumn<MemberTM,String> colMemberName;
    public TableColumn<MemberTM,String> colMemberAddress;
    public TableColumn<MemberTM,String> colMemberEmail;
    public TableColumn<MemberTM,String> colMemberContact;


    public final MemberServiceIMPL service = (MemberServiceIMPL) ServiceFactory.getInstance().getService(ServiceTypes.MEMBER_SERVICE);
    //down casting
    public void initialize(){
        loadTableData();
        visualizeTable();
    }

    private void visualizeTable() {
        colMemberID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colMemberAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMemberEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMemberContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    public void txtMemberIDOnAction(ActionEvent actionEvent) {
        Optional<MemberDTO> search = null;
        try {
            search = service.search(txtMemberID.getText());
        } catch (MemberException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            return;
        }
        if (search.isPresent()){
            setDataToField(search.get());
        }else{
            new Alert(Alert.AlertType.ERROR,"Member Not Found").show();

        }
    }

    public void txtMemberEmailOnAction(ActionEvent actionEvent) {

    }

    public void txtMemberContactOnAction(ActionEvent actionEvent) {

    }

    public void btnSaveOnAction(ActionEvent actionEvent){
        MemberDTO memberDTO = collectData();
        boolean isMemberSaved = false;
        String error = "Unexpected Error";
        try {
            isMemberSaved = service.add(memberDTO);
        } catch (MemberException e) {
            error = e.getMessage();
        }
        if(isMemberSaved){
            new Alert(Alert.AlertType.INFORMATION,"Member Saved Successfully").show();
            loadTableData();
            clearField();
        }else
            new Alert(Alert.AlertType.ERROR,error).show();
    }
    public void btnDeleteOnAction(ActionEvent actionEvent){
        String memberID = txtMemberID.getText();
        boolean delete = false;
        String errormessage = "User canceled - Not Deleted";
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete Member?", ButtonType.YES, ButtonType.NO).showAndWait();
        if(result.isPresent()){
            ButtonType buttonType = result.get();
            boolean equals = buttonType.equals(buttonType.YES);
            if(equals){
                try {
                    delete = service.delete(memberID);
                    if(!delete){
                        errormessage = "User not found - Check ID";
                    }
                }catch (MemberException e){
                    errormessage = e.getMessage();
                }
            }
        }
        if(delete){
            new Alert(Alert.AlertType.INFORMATION, "Member Delete Successfully").show();
            loadTableData();
        }
        else {
            new Alert(Alert.AlertType.ERROR,errormessage).show();
        }
    }
    public void btnUpdateOnAction(ActionEvent actionEvent) {
        MemberDTO memberDTO = collectData();
        boolean isUpdated = false;
        String errorMessage = "Data is Already Same - Not Updated";
        try {
            isUpdated = service.update(memberDTO);
        } catch (MemberException e) {
            errorMessage = e.getMessage();
        }
        if (isUpdated){
            new Alert(Alert.AlertType.INFORMATION,"Member Updated Success").show();
            clearField();
            loadTableData();
        }else {
            new Alert(Alert.AlertType.ERROR,errorMessage).show();
        }
    }
    public void loadTableData(){
        try {
            List<MemberTM> list = new ArrayList<>();
            List<MemberDTO> all = service.getAll();
            for (MemberDTO memberDTO : all) {
                MemberTM memberTM = convertMemberDTOtoTM(memberDTO);
                list.add(memberTM);
            }
            ObservableList<MemberTM> memberTMS = FXCollections.observableArrayList(list);
            tblMember.setItems(memberTMS);
        } catch (MemberException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearField();
    }

    public MemberDTO collectData(){
        String id = txtMemberID.getText();
        String name = txtMemberName.getText();
        String address = txtMemberAddress.getText();
        String email = txtMemberEmail.getText();
        String contact = txtMemberContact.getText();
        MemberDTO memberDTO = new MemberDTO(id,name,address,email,contact);

        return memberDTO;
    }
    public void setDataToField(MemberDTO member){
        txtMemberID.setText(member.getId());
        txtMemberName.setText(member.getName());
        txtMemberAddress.setText(member.getAddress());
        txtMemberEmail.setText(member.getEmail());
        txtMemberContact.setText(member.getContact());
    }
    public void clearField(){
        txtMemberID.clear();
        txtMemberName.clear();
        txtMemberAddress.clear();
        txtMemberEmail.clear();
        txtMemberContact.clear();
    }
    public MemberTM convertMemberDTOtoTM(MemberDTO memberDTO){
        MemberTM memberTM = new MemberTM();
        memberTM.setId(memberDTO.getId());
        memberTM.setName(memberDTO.getName());
        memberTM.setAddress(memberDTO.getAddress());
        memberTM.setEmail(memberDTO.getEmail());
        memberTM.setContact(memberDTO.getContact());
        return memberTM;
    }
}
