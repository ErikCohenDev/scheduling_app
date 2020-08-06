package main.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import main.db.Store;
import main.model.AppointmentModel;

import java.net.URL;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Report implements Initializable {
    final List<String> months = Arrays. asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    final List<String> typesList = Store.getAppointments().stream().map(appointment -> appointment.getType()).collect(Collectors.toList());

    @FXML
    private ComboBox typesComboBox;
    @FXML
    private ComboBox monthComboBox;

    @FXML
    private TableView<String> appointmentReportTable;
    @FXML
    private TableColumn<Object, String> appointmentReportMonthCol;
    @FXML
    private TableColumn<Object, String> appointmentReportAmountCol;

    @FXML
    private TableView consultantReportTable;
    @FXML
    private TableColumn consultantReportUserCol;
    @FXML
    private TableColumn consultantReportAppointmentCol;

    @FXML
    private TableView cityReportTable;
    @FXML
    private TableColumn cityReportTableCustomerCol;
    @FXML
    private TableColumn cityReportTableCityCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typesComboBox.getItems().addAll(typesList);
        monthComboBox.getItems().addAll(months);
        this.generateCustomerReportByCity();
    }

    public void generateAppointmentReportByUser() {
        List<String> customerCities = Store.getCustomers().stream()
                .map(appointment -> appointment.getCityName())
                .collect(Collectors.toList());
        ObservableList<String> observableCities = FXCollections.observableArrayList(customerCities);
        this.consultantReportTable.setItems(observableCities);
        this.consultantReportUserCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.consultantReportAppointmentCol.setCellValueFactory(new PropertyValueFactory("name"));
    }

    public void generateCustomerReportByCity() {
        List<String> customerCities = Store.getCustomers().stream()
                .map(appointment -> appointment.getCityName())
                .collect(Collectors.toList());
        ObservableList<String> observableCities = FXCollections.observableArrayList(customerCities);
        this.cityReportTable.setItems(observableCities);
        this.cityReportTableCustomerCol.setCellValueFactory(month -> new SimpleStringProperty(month.toString()));
        this.cityReportTableCityCol.setCellValueFactory(new PropertyValueFactory("name"));
    }

    public void generateAppointmentByType() {
        String appointmentType = typesComboBox.getValue().toString();
        List<AppointmentModel> filteredAppointments = Store.getAppointments().stream()
                .filter(appointment -> appointment.getType() == appointmentType)
                .collect(Collectors.toList());
        ObservableList<String> observableMonths = FXCollections.observableArrayList(months);
        this.appointmentReportTable.setItems(observableMonths);
        this.appointmentReportMonthCol.setCellValueFactory(month -> new SimpleStringProperty((String) month.getValue()));
        this.appointmentReportAmountCol.setCellValueFactory(month ->  new SimpleStringProperty(Long.toString(filteredAppointments.stream()
                .filter(appointment -> {
                    return appointment.getStartDate().getMonth().getDisplayName(TextStyle.FULL, Locale.US).equals(month.getValue());
                })
                .count()
                )
            )
        );
    }

    public void goBack() throws Exception {
        Main.goToDashboard();
    }
}