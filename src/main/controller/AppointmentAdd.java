package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import main.db.Store;
import main.model.CustomerModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentAdd implements Initializable {
    @FXML
    private TextInputControl titleInput;
    @FXML
    private TextInputControl descriptionInput;
    @FXML
    private TextInputControl typeInput;
    @FXML
    private TextInputControl urlInput;
    @FXML
    private TextInputControl locationInput;
    @FXML
    private TextInputControl countryInput;
    @FXML
    private ChoiceBox customerChoiceBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextInputControl startHourInput;
    @FXML
    private TextInputControl startMinInput;
    @FXML
    private ChoiceBox startPeriodChoiceBox;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextInputControl endHourInput;
    @FXML
    private TextInputControl endMinInput;
    @FXML
    private ChoiceBox endPeriodChoiceBox;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<CustomerModel> customerList = FXCollections.observableArrayList();
        customerList.setAll(Store.getCustomers());
        customerChoiceBox.setItems(customerList);

        endPeriodChoiceBox.getItems().addAll("AM", "PM");
        startPeriodChoiceBox.getItems().addAll("AM", "PM");
    }
    public void add() {

    }

    public void goBack() {

    }
}
