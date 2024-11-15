package lk.codeschool.libry_management_system.controller.controller.sub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.codeschool.libry_management_system.controller.dto.custom.AuthorDTO;
import lk.codeschool.libry_management_system.controller.dto.custom.PublisherDTO;
import lk.codeschool.libry_management_system.controller.service.custom.AuthorService;
import lk.codeschool.libry_management_system.controller.service.custom.PublisherService;
import lk.codeschool.libry_management_system.controller.service.util.OtherDependecies;
import lk.codeschool.libry_management_system.controller.service.util.ServiceFactory;
import lk.codeschool.libry_management_system.controller.service.util.ServiceTypes;
import lk.codeschool.libry_management_system.controller.tm.AuthorTM;
import lk.codeschool.libry_management_system.controller.tm.PublisherTM;
import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ManageAuthorsAndPublishersFormController {
    public TextField txtPublisherID;
    public TextField txtPublisherName;
    public TextField txtPublisherLocation;
    public TextField txtPublisherContact;
    public TableView<PublisherTM> tblPublisher;
    public TableColumn<PublisherTM,Integer> colPublisherID;
    public TableColumn<PublisherTM,String> colPublisherName;
    public TableColumn<PublisherTM,String> colPublisherContact;
    public TextField txtAuthorID;
    public TextField txtAuthorName;
    public TextField txtAuthorContacts;
    public TableView<AuthorTM> tblAuthor;
    public TableColumn<AuthorTM,Integer> colAuthorID;
    public TableColumn<AuthorTM,String> colAuthorName;
    public TableColumn<AuthorTM,String> colAuthorContact;


    private final PublisherService publisherService = (PublisherService) ServiceFactory.getInstance()
            .getService(ServiceTypes.PUBLISHER_SERVICE);

    private final ModelMapper mapper = OtherDependecies.getInstance().getMapper();

    private final AuthorService authorService = (AuthorService) ServiceFactory.getInstance()
            .getService(ServiceTypes.AUTHOR_SERVICE);

    public void initialize(){
        txtPublisherID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtPublisherID.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        visualizeData();
        loadAuthorTableData();
        loadPublisherTableData();
    }

    private void visualizeData() {
        colAuthorID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAuthorContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        colPublisherID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPublisherName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPublisherContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    public void btnPublisherSaveOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectPublisherData();
        try {
            boolean isSaved = publisherService.add(publisherDTO);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Saved Success").show();
                loadPublisherTableData();
                clearPublisherField();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Not Saved").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnPublisherUpdateOnAction(ActionEvent actionEvent) {
        PublisherDTO dto = collectPublisherData();
        if (dto.getId() == 0){
            new Alert(Alert.AlertType.ERROR,"Invalid ID - please Enter valid Id").show();
            return;
        }
        try {
            boolean updated = publisherService.update(dto);
            if(updated){
                new Alert(Alert.AlertType.INFORMATION,"Updated Success").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Update").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnPublisherDeleteOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectPublisherData();
        if(publisherDTO.getId() == 0){
            new Alert(Alert.AlertType.ERROR,"Invalid ID - please Enter valid Id").show();
            return;
        }
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.isPresent()){
            if (buttonType.get().equals(ButtonType.YES)){
                try {
                    boolean delete = publisherService.delete(publisherDTO.getId());
                    if (delete){
                        new Alert(Alert.AlertType.INFORMATION,"Deleted Success").show();
                        loadPublisherTableData();
                        return;
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Not Deleted").show();
                    }
                } catch (ServiceException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
        }
    }

    public void btnPublisherClearOnAction(ActionEvent actionEvent) {
        clearPublisherField();
    }

    public void btnAuthorSaveOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        try {
            boolean added = authorService.add(authorDTO);
            if (added){
                new Alert(Alert.AlertType.INFORMATION,"Saved Success").show();
                clearAuthorField();
                loadAuthorTableData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Saved").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    public void btnAuthorUpdateOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        if (authorDTO.getId() == 0){
            new Alert(Alert.AlertType.ERROR,"Invalid ID - please Enter valid Id").show();
            return;
        }
        try {
            boolean updated = authorService.update(authorDTO);
            if (updated){
                new Alert(Alert.AlertType.INFORMATION,"Update Success").show();
                clearAuthorField();
                loadAuthorTableData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Updated").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnAuthorDeleteOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        if (authorDTO.getId() == 0){
            new Alert(Alert.AlertType.ERROR,"Invalid ID - please Enter valid Id").show();
            return;
        }
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.isPresent()){
            if(buttonType.get().equals(ButtonType.YES)){
                try {
                    boolean delete = authorService.delete(authorDTO.getId());
                    if (delete){
                        new Alert(Alert.AlertType.INFORMATION,"Deleted Successfully").show();
                        loadAuthorTableData();
                        return;
                    }
                    else {
                        new Alert(Alert.AlertType.ERROR,"Not Deleted").show();
                    }
                } catch (ServiceException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
        }
    }

    public void btnAuthorClearOnAction(ActionEvent actionEvent) {
        clearAuthorField();
    }

    public void clearPublisherField(){
        txtPublisherID.clear();
        txtPublisherName.clear();
        txtPublisherContact.clear();
        txtPublisherLocation.clear();
    }
    public void clearAuthorField(){
        txtAuthorID.clear();
        txtAuthorName.clear();
        txtAuthorContacts.clear();
    }

    private PublisherDTO collectPublisherData(){
        String id = txtPublisherID.getText();
        String name = txtPublisherName.getText();
        String location = txtPublisherLocation.getText();
        String contact = txtPublisherContact.getText();

        int idNumber = 0;
        try {
            idNumber = Integer.parseInt(id);
        }catch (NumberFormatException ex){

        }
        return PublisherDTO.builder().id(idNumber).name(name).location(location).contact(contact).build();
    }

    private AuthorDTO collectAuthorData(){
        String authorID = txtAuthorID.getText();
        String authorName = txtAuthorName.getText();
        String authorContact = txtPublisherContact.getText();

        int idAuthor = 0;
        try {
            idAuthor = Integer.parseInt(authorID);
        }catch (NumberFormatException ex){

        }
        return AuthorDTO.builder().id(idAuthor).name(authorName).contact(authorContact).build();

    }

    public void loadAuthorTableData(){
        try {
            tblAuthor.setItems(FXCollections.observableArrayList(authorService.getAll().stream()
                    .map(this::convertAuthorDTOtoTM).toList()));
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    public void loadPublisherTableData(){
        try {
            tblPublisher.setItems(FXCollections.observableArrayList(   publisherService.getAll().stream()
                    .map(this::convertPublisherDTOtoTM).toList()));
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }
    private void setPublisherDataToFields(PublisherDTO publisherDTO) {
        txtPublisherID.setText(String.valueOf(publisherDTO.getId()));
        txtPublisherName.setText(publisherDTO.getName());
        txtPublisherContact.setText(publisherDTO.getContact());
        txtPublisherLocation.setText(publisherDTO.getLocation());
    }

    private void setAuthorDataToFields(AuthorDTO authorDTO) {
        txtAuthorID.setText(String.valueOf(authorDTO.getId()));
        txtAuthorName.setText(authorDTO.getName());
        txtAuthorContacts.setText(authorDTO.getContact());
    }

    public AuthorTM convertAuthorDTOtoTM(AuthorDTO authorDTO){
        return mapper.map(authorDTO,AuthorTM.class);
    }
    public PublisherTM convertPublisherDTOtoTM(PublisherDTO publisherDTO){
        return mapper.map(publisherDTO,PublisherTM.class);
    }

    public void txtAuthorOnAction(ActionEvent actionEvent) {
        AuthorDTO authorDTO = collectAuthorData();
        try {
            Optional<AuthorDTO> search = authorService.search(authorDTO.getId());
            if (search.isPresent()){
                setAuthorDataToFields(search.get());
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Author not found or Invalid ID").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void txtPublisherOnAction(ActionEvent actionEvent) {
        PublisherDTO publisherDTO = collectPublisherData();
        try {
            Optional<PublisherDTO> search = publisherService.search(publisherDTO.getId());
            if (search.isPresent()){
                setPublisherDataToFields(search.get());
            }else {
                new Alert(Alert.AlertType.ERROR,"Publisher not found or Invalid ID").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void tblAuthorsOnMouseClick(MouseEvent mouseEvent) {
        AuthorTM selectedItem = tblAuthor.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            txtAuthorID.setText(String.valueOf(selectedItem.getId()));
            txtAuthorOnAction(null);
        }
    }

    public void tblPublisherOnMouseClick(MouseEvent mouseEvent) {
        PublisherTM selectedItem = tblPublisher.getSelectionModel().getSelectedItem();
        if (selectedItem != null){
            txtPublisherID.setText(String.valueOf(selectedItem.getId()));
            txtPublisherOnAction(null);
        }
    }
}
