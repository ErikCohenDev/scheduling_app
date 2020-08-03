package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import main.Main;
import main.db.Store;
import main.model.AppointmentModel;
import main.model.CustomerModel;

import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentModify implements Initializable {
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
        customerChoiceBox.setValue(customerList.stream().findFirst().get());
        endPeriodChoiceBox.getItems().addAll("AM", "PM");
        startPeriodChoiceBox.getItems().addAll("AM", "PM");
        startPeriodChoiceBox.setValue("AM");
        endPeriodChoiceBox.setValue("AM");
    }

    public void initData(AppointmentModel appointment) {
        titleInput.setText(appointment.getTitle());
        descriptionInput.setText(appointment.getDescription());
        typeInput.setText(appointment.getType());
        urlInput.setText(appointment.getUrl());
        locationInput.setText(appointment.getLocation());
        customerChoiceBox.setValue(0);
        startDatePicker.setValue(appointment.getStartDate().toLocalDate());
        startHourInput.setText(String.valueOf(appointment.getStartDate().getHour()));
        startMinInput.setText(String.valueOf(appointment.getStartDate().getMinute()));
        startPeriodChoiceBox.setValue("AM");
        endDatePicker.setValue(appointment.getEndDate().toLocalDate());
        endHourInput.setText(String.valueOf(appointment.getEndDate().getHour()));
        endMinInput.setText(String.valueOf(appointment.getEndDate().getMinute()));
        endPeriodChoiceBox.setValue("PM");
    }

    public void update() {

    }

    public void goBack() throws Exception {
        Main.goToAppointment();
    }
}