package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import main.db.Store;
import main.model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Customer implements Initializable {
    @FXML
    private TableView<CustomerModel> customerTable;
    @FXML
    private TableColumn<CustomerModel, Integer> customerIdCol;
    @FXML
    private TableColumn<CustomerModel, String> customerNameCol;
    @FXML
    private TableColumn<CustomerModel, String> customerPhoneCol;
    @FXML
    private TableColumn<CustomerModel, String> customerAddressCol;
    @FXML
    private TableColumn<CustomerModel, String> customerAddress2Col;
    @FXML
    private TableColumn<CustomerModel, String> customerCityCol;
    @FXML
    private TableColumn<CustomerModel, String> customerZipCol;
    @FXML
    private TableColumn<CustomerModel, String> customerCountryCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<CustomerModel> observableCustomers = FXCollections.observableArrayList(Store.getCustomers());
        this.customerTable.setItems(observableCustomers);
        this.customerIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.customerNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        this.customerPhoneCol.setCellValueFactory(new PropertyValueFactory("phone"));
        this.customerAddressCol.setCellValueFactory(new PropertyValueFactory("address"));
        this.customerAddress2Col.setCellValueFactory(new PropertyValueFactory("address2"));
        this.customerCityCol.setCellValueFactory(new PropertyValueFactory("city"));
        this.customerZipCol.setCellValueFactory(new PropertyValueFactory("zip"));
        this.customerCountryCol.setCellValueFactory(new PropertyValueFactory("country"));
    }

    public void add() throws Exception {
        Main.goToCustomerAdd();
    }

    public void goBack() throws Exception {
        Main.goToDashboard();
    }
}