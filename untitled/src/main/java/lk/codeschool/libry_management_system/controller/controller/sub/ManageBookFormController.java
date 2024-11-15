package lk.codeschool.libry_management_system.controller.controller.sub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lk.codeschool.libry_management_system.controller.cm.CatogoryCM;
import lk.codeschool.libry_management_system.controller.cm.PublisherCM;
import lk.codeschool.libry_management_system.controller.dto.custom.BookDTO;
import lk.codeschool.libry_management_system.controller.repo.custom.BookRepo;
import lk.codeschool.libry_management_system.controller.repo.util.RepoFactory;
import lk.codeschool.libry_management_system.controller.repo.util.RepoTypes;
import lk.codeschool.libry_management_system.controller.service.custom.AuthorService;
import lk.codeschool.libry_management_system.controller.service.custom.BookService;
import lk.codeschool.libry_management_system.controller.service.custom.CategoryService;
import lk.codeschool.libry_management_system.controller.service.custom.PublisherService;
import lk.codeschool.libry_management_system.controller.service.custom.impl.BookServiceIMPL;
import lk.codeschool.libry_management_system.controller.service.util.OtherDependecies;
import lk.codeschool.libry_management_system.controller.service.util.ServiceFactory;
import lk.codeschool.libry_management_system.controller.service.util.ServiceTypes;
import lk.codeschool.libry_management_system.controller.tm.AuthorTMWithCheckBox;
import lk.codeschool.libry_management_system.controller.tm.BookTM;
import lk.codeschool.libry_management_system.controller.tm.CategoryTMWithCheckBox;
import lk.codeschool.libry_management_system.controller.util.exception.ServiceException;
import lk.codeschool.libry_management_system.controller.util.exception.custom.BookException;
import lk.codeschool.libry_management_system.controller.util.exception.custom.MemberException;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ManageBookFormController {
    public TextField txtBookID;
    public TextField txtBookName;
    public TextField txtBookISBN;
    public TextField txtBookPrice;
    public ComboBox<PublisherCM> cmbSelectPublisher;
    public ComboBox<CatogoryCM> cmbMainCatogary;

    public TableView<CategoryTMWithCheckBox> tblSubCatogary;
    public TableColumn<CategoryTMWithCheckBox,String> colCatogaryName;
    public TableColumn<CategoryTMWithCheckBox, CheckBox> colOption;

    public TableView tblBook;
    public TableColumn colBookID;
    public TableColumn colBookName;
    public TableColumn colBookISBN;
    public TableColumn colBookPrice;

    public TableView<AuthorTMWithCheckBox> tblAuthors;
    public TableColumn<AuthorTMWithCheckBox,String> colAuthorName;
    public TableColumn<AuthorTMWithCheckBox,CheckBox> colAuthorOption;

    private final PublisherService publisherService = (PublisherService) ServiceFactory
            .getInstance().getService(ServiceTypes.PUBLISHER_SERVICE);
    private final CategoryService categoryService = (CategoryService) ServiceFactory
            .getInstance().getService(ServiceTypes.CATEGORY_SERVICE);
    private final AuthorService authorService = (AuthorService) ServiceFactory
            .getInstance().getService(ServiceTypes.AUTHOR_SERVICE);
    private final BookService bookService = (BookService) ServiceFactory
            .getInstance().getService(ServiceTypes.BOOK_SERVICE);

    public void initialize(){
        addConverterToComboBox();
        setCellValueFactory();
        try {
            //for get combo box data
            List<PublisherCM> publishers = publisherService.getAll().stream().map(e -> PublisherCM.builder().id(e.getId()).name(e.getName()).build()).toList();
            cmbSelectPublisher.setItems(FXCollections.observableArrayList(publishers));

            loadCatogoryData();
            loadAuthorData();
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadCatogoryData() throws ServiceException {
        //for get combo box data
        List<CatogoryCM> catogoryies = categoryService.getAll().stream().map(e -> CatogoryCM.builder().id(e.getId()).name(e.getName()).build()).toList();
        cmbMainCatogary.setItems(FXCollections.observableArrayList(catogoryies));

        List<CategoryTMWithCheckBox> list = catogoryies.stream().map(e -> CategoryTMWithCheckBox.builder().id(e.getId()).name(e.getName()).checkBox(new CheckBox()).build()).toList();
        tblSubCatogary.setItems(FXCollections.observableArrayList(list));
    }
    public void loadAuthorData() throws ServiceException {

        List<AuthorTMWithCheckBox> list = authorService.getAll().stream().map(e -> AuthorTMWithCheckBox.builder().id(e.getId()).name(e.getName()).checkBox(new CheckBox()).build()).toList();
        tblAuthors.setItems(FXCollections.observableArrayList(list));
    }

    public void setCellValueFactory(){
        colCatogaryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("checkBox"));

        colAuthorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
    }

    public void addConverterToComboBox(){
        //convert combo box data
        cmbSelectPublisher.setConverter(new StringConverter<PublisherCM>() {
            @Override
            public String toString(PublisherCM publisherCM) {
                return publisherCM.getName();
            }

            @Override
            public PublisherCM fromString(String s) {
                return null;
            }
        });

        cmbMainCatogary.setConverter(new StringConverter<CatogoryCM>() {
            @Override
            public String toString(CatogoryCM catogoryCM) {
                return catogoryCM.getName();
            }

            @Override
            public CatogoryCM fromString(String s) {
                return null;
            }
        });
    }

    public void txtBookIDOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        BookDTO bookDTO = collectData();
        try {
            bookService.add(bookDTO);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearField();
    }
    private void loadTableData() {

    }

//    public BookDTO collectData(){
//        String id = txtBookID.getId();
//        String name = txtBookName.getText();
//        String isbn = txtBookISBN.getText();
//        String price = txtBookPrice.getText();
//
//        int idNum = Integer.parseInt(id);
//        double priceNum = Double.parseDouble(price);
//        BookDTO bookDTO  = new BookDTO();
//        bookDTO.setId(idNum);
//        bookDTO.setName(name);
//        bookDTO.setIsbn(isbn);
//        bookDTO.setPrice(priceNum);
//
//        return bookDTO;
//    }
    public void setDataToFields(BookDTO book){
        txtBookID.setText(String.valueOf(book.getId()));
        txtBookName.setText(book.getName());
        txtBookISBN.setText(book.getIsbn());
        txtBookPrice.setText(String.valueOf(book.getPrice()));
    }

    public void clearField(){
        txtBookID.clear();
        txtBookName.clear();
        txtBookISBN.clear();
        txtBookPrice.clear();
    }

    private BookTM convertBookToTM(BookDTO book){
        BookTM bookTM = new BookTM();
        bookTM.setId(book.getId());
        bookTM.setName(book.getName());
        bookTM.setIsbn(book.getIsbn());
        bookTM.setPrice(book.getPrice());
        return bookTM;
    }

    public void btnManageCategoryOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sub/manage_catogory_form.fxml"));
            Parent load = loader.load();
            ManageCatogoryFormController controller = loader.getController();   //ui mappinf
            controller.setBaseController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(load));

            stage.initModality(Modality.APPLICATION_MODAL);    //this code is used to when popup window was load and user can't access main dashboard
            stage.initOwner(txtBookID.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BookDTO collectData(){
        int bookId = 0;
        String bookName = txtBookName.getText();
        String bookIsbn = txtBookISBN.getText();
        double price = 0;
        int publisherId = 0;
        int mainCatogary = 0;
        int count = 0;

        try {
            bookId = Integer.parseInt(txtBookID.getText());

        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        try {
            price = Double.parseDouble(txtBookPrice.getText());
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
        try {
            publisherId = cmbSelectPublisher.getSelectionModel().getSelectedItem().getId();
            count++;
            mainCatogary = cmbMainCatogary.getSelectionModel().getSelectedItem().getId();
            count++;
        }catch (NullPointerException e){
            String er = count == 0 ? "Publisher" : "Category";
            new Alert(Alert.AlertType.ERROR,"Select "+er).show();
        }
//        ArrayList<Integer> authorIds = new ArrayList<>();
//        ObservableList<AuthorTMWithCheckBox> Items = tblAuthors.getItems();
//        for (AuthorTMWithCheckBox item : Items) {
//            if (item.getCheckBox().isSelected()) {
//                authorIds.add(item.getId());
//            }
//        }
        List<Integer> authorIds = tblAuthors.getItems().stream().filter(e -> e.getCheckBox().isSelected()).map(e -> e.getId()).toList();
        List<Integer> subCategoryIds = tblSubCatogary.getItems().stream().filter(e -> e.getCheckBox().isSelected()).map(e -> e.getId()).toList();

        BookDTO build = BookDTO.builder()
                .id(bookId)
                .name(bookName)
                .isbn(bookIsbn)
                .price(price)
                .publisherId(publisherId)
                .mainCategory(mainCatogary)
                .subCategoryIds(subCategoryIds)
                .authorIds(authorIds)
                .build();
        return build;
    }
}
