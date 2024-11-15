package lk.codeschool.libry_management_system.controller.controller.sub;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.codeschool.libry_management_system.controller.dto.custom.CategoryDTO;
import lk.codeschool.libry_management_system.controller.service.custom.impl.CategoryServiceIMPL;
import lk.codeschool.libry_management_system.controller.service.util.OtherDependecies;
import lk.codeschool.libry_management_system.controller.service.util.ServiceFactory;
import lk.codeschool.libry_management_system.controller.service.util.ServiceTypes;
import lk.codeschool.libry_management_system.controller.tm.AuthorTM;
import lk.codeschool.libry_management_system.controller.tm.CategoryTM;
import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

public class ManageCatogoryFormController {
    public TextField txtCategoryID;
    public TextField txtCategoryName;
    public TableView<CategoryTM> tblCatogory;
    public TableColumn<CategoryTM,Integer> colCatogoryID;
    public TableColumn<CategoryTM,String> colCatogoryName;
    private ManageBookFormController baseController;

    CategoryServiceIMPL service =(CategoryServiceIMPL) ServiceFactory.getInstance().getService(ServiceTypes.CATEGORY_SERVICE);
    ModelMapper mapper = OtherDependecies.getInstance().getMapper();

    public void initialize(){
        setCellValueFactories();
        loadTableData();
    }

    private void setCellValueFactories() {
        colCatogoryID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCatogoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void loadTableData() {
        try {
            List<CategoryTM> list = service.getAll().stream().map(e -> mapper.map(e, CategoryTM.class)).toList();
            tblCatogory.setItems(FXCollections.observableArrayList(list));
            if(baseController!=null)baseController.loadCatogoryData();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }


    public void btnCatogorySaveOnAction(ActionEvent actionEvent) {
        CategoryDTO categoryDTO = collectData();
        try {
            boolean added = service.add(categoryDTO);
            if (added){
                new Alert(Alert.AlertType.INFORMATION,"Saved").show();
                clearFields();
                loadTableData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Saved").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnCatogoryUpdateOnAction(ActionEvent actionEvent) {
        CategoryDTO categoryDTO = collectData();
        if (categoryDTO.getId() == 0){
            new Alert(Alert.AlertType.ERROR,"Invalid Id").show();
            return;
        }
        try {
            boolean updated = service.update(categoryDTO);
            if (updated){
                new Alert(Alert.AlertType.INFORMATION,"Updated Success").show();
                clearFields();
                loadTableData();
            }else {
                new Alert(Alert.AlertType.ERROR,"Not Updated").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnCatogoryDeleteOnAction(ActionEvent actionEvent) {
        CategoryDTO categoryDTO = collectData();
        if (categoryDTO.getId()==0){
            new Alert(Alert.AlertType.ERROR,"Invalid Id").show();
            return;
        }
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure :(", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.isPresent()){
            if (buttonType.get().equals(ButtonType.YES)){
                try {
                    boolean deleted = service.delete(categoryDTO.getId());
                    if (deleted){
                        new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                        loadTableData();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Not Deleted").show();
                    }
                } catch (ServiceException e) {
                    new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
                }
            }
        }
    }

    public void btnCatogoryClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    public void clearFields(){
        txtCategoryID.clear();
        txtCategoryName.clear();
    }

    private CategoryDTO collectData(){
        String id = txtCategoryID.getText();
        String name = txtCategoryName.getText();

        int idText = 0;
        try {
            idText = Integer.parseInt(id);
        }catch (NumberFormatException ex){
        }
        return CategoryDTO.builder().id(idText).name(name).build();
    }

    public void txtSearchIdOnAction(ActionEvent actionEvent) {
        CategoryDTO categoryDTO = collectData();
        try {
            Optional<CategoryDTO> searched = service.search(categoryDTO.getId());
            if (searched.isPresent()){
                txtCategoryName.setText(searched.get().getName());
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid Id").show();
            }
        } catch (ServiceException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void setBaseController(ManageBookFormController baseController) {
        this.baseController = baseController;
    }
}
