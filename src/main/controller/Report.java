package main.controller;

import javafx.beans.property.SimpleIntegerProperty;
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
import main.model.UserModel;

import java.net.URL;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Report implements Initializable {
    final List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    final List<String> typesList = Store.getAllAppointmentTypes();

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
    private TableColumn<UserModel, String> consultantReportUserCol;
    @FXML
    private TableColumn<UserModel, Integer>consultantReportAppointmentCol;

    @FXML
    private TableView<String> cityReportTable;
    @FXML
    private TableColumn<Object, Integer> cityReportTableCustomerCol;
    @FXML
    private TableColumn<Object, String> cityReportTableCityCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (typesList != null) {
            typesComboBox.getItems().addAll(typesList);
        }
        monthComboBox.getItems().addAll(months);
        this.generateCustomerReportByCity();
    }

    public void generateAppointmentReportByUser() {
        List<UserModel> users = Store.getUsers();
        ObservableList<UserModel> observableUsers = FXCollections.observableArrayList(users);
        this.consultantReportTable.setItems(observableUsers);
        this.consultantReportUserCol.setCellValueFactory(new PropertyValueFactory("username"));
        this.consultantReportAppointmentCol.setCellValueFactory(user -> {
            List<UserModel> newUsersList = Store.getUsers();
            ObservableList<UserModel> newObservableUsersList = FXCollections.observableArrayList(newUsersList);
            FXCollections.copy(observableUsers, newObservableUsersList);
            this.consultantReportTable.setItems(newObservableUsersList);
            return new SimpleIntegerProperty(
                            user.getValue().allAppointmentCountByMonth(user.getValue().getId(), monthComboBox.getValue().toString())
                    ).asObject();
                }
        );
    }

    public void generateCustomerReportByCity() {
        List<String> customerCities = Store.getCustomers().stream()
                .map(appointment -> appointment.getCityName())
                .distinct()
                .collect(Collectors.toList());
        ObservableList<String> observableCities = FXCollections.observableArrayList(customerCities);
        this.cityReportTable.setItems(observableCities);
        this.cityReportTableCustomerCol.setCellValueFactory(city -> new SimpleIntegerProperty((int) Store.getCustomers().stream()
                .filter(customer -> customer.getCityName().equals(city.getValue())).count()
            ).asObject()
        );
        this.cityReportTableCityCol.setCellValueFactory(city -> new SimpleStringProperty((String) city.getValue()));
    }

    public void generateAppointmentByType() {
        String appointmentType = typesComboBox.getValue().toString();
        List<AppointmentModel> filteredAppointments = Store.getAppointments().stream()
                .filter(appointment -> appointment.getType() == appointmentType)
                .collect(Collectors.toList());
        ObservableList<String> observableMonths = FXCollections.observableArrayList(months);
        this.appointmentReportTable.setItems(observableMonths);
        this.appointmentReportMonthCol.setCellValueFactory(month -> new SimpleStringProperty((String) month.getValue()));
        this.appointmentReportAmountCol.setCellValueFactory(month ->  new SimpleStringProperty(Long.toString(
                filteredAppointments.stream()
                    .filter(appointment -> appointment.getStartDate().getMonth().getDisplayName(TextStyle.FULL, Locale.US).equals(month.getValue()))
                    .count()
                )
            )
        );
    }

    public void goBack() throws Exception {
        Main.goToDashboard();
    }
}