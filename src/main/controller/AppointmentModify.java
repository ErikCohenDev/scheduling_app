package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import main.Main;
import main.db.Authenticate;
import main.db.DBAppointment;
import main.db.Store;
import main.model.AppointmentModel;
import main.model.CustomerModel;
import main.model.UserModel;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AppointmentModify implements Initializable {
    ObservableList<CustomerModel> customerList = FXCollections.observableArrayList();
    AppointmentModel appointment;
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
    private ChoiceBox<CustomerModel> customerChoiceBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TextInputControl startHourInput;
    @FXML
    private TextInputControl startMinInput;
    @FXML
    private ChoiceBox<String> startPeriodChoiceBox;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextInputControl endHourInput;
    @FXML
    private TextInputControl endMinInput;
    @FXML
    private ChoiceBox<String> endPeriodChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerList.setAll(Store.getCustomers());
        customerChoiceBox.setItems(customerList);
        customerChoiceBox.setValue(customerList.stream().findFirst().get());
        endPeriodChoiceBox.getItems().addAll("AM", "PM");
        startPeriodChoiceBox.getItems().addAll("AM", "PM");
        startPeriodChoiceBox.setValue("AM");
        endPeriodChoiceBox.setValue("AM");
    }

    public void initData(AppointmentModel appointment) {
        this.appointment = appointment;
        int startDateHour = appointment.getStartDate().getHour();
        int startDateMin = appointment.getStartDate().getMinute();

        int endDateHour = appointment.getEndDate().getHour();
        int endDateMin = appointment.getEndDate().getMinute();

        titleInput.setText(appointment.getTitle());
        descriptionInput.setText(appointment.getDescription());
        typeInput.setText(appointment.getType());
        urlInput.setText(appointment.getUrl());
        locationInput.setText(appointment.getLocation());
        customerChoiceBox.setValue(customerList.stream()
                .filter(customer -> customer.getId() == appointment.getCustomerId())
                .findFirst()
                .get()
        );
        startDatePicker.setValue(appointment.getStartDate().toLocalDate());
        startHourInput.setText(String.valueOf(appointment.getStartDate().getHour()));
        startMinInput.setText(startDateMin == 0 ? "00" : String.valueOf(startDateMin));
        startPeriodChoiceBox.setValue(startDateHour < 12 ? "AM" : "PM");

        endDatePicker.setValue(appointment.getEndDate().toLocalDate());
        endHourInput.setText(String.valueOf(endDateHour));
        endMinInput.setText(endDateMin == 0 ? "00" : String.valueOf(endDateMin));
        endPeriodChoiceBox.setValue(endDateHour < 12 ? "AM" : "PM");
    }

    public void update() throws Exception {
        String title = titleInput.getText();
        String description = descriptionInput.getText();
        String type = typeInput.getText();
        String url = urlInput.getText();
        String location = locationInput.getText();
        CustomerModel customer = (CustomerModel) customerChoiceBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        int startHour = Integer.parseInt(startHourInput.getText());
        int startMin = Integer.parseInt(startMinInput.getText());
        LocalDate endDate = endDatePicker.getValue();
        int endHour = Integer.parseInt(endHourInput.getText());
        int endMin = Integer.parseInt(endMinInput.getText());

        if (!validForm(title, description, type, url, location, startDate, endDate)) {
            return;
        }

        LocalDateTime startDateTime = startDate.atTime(startHour, startMin);
        LocalDateTime endDateTime = endDate.atTime(endHour, endMin);

        UserModel user = Authenticate.user;
        DBAppointment.update(appointment.getId(), title, description, type, url, location, customer, startDateTime, endDateTime, user);
        Store.refreshAppointments();
        goBack();
    }

    private boolean validForm(String title, String description, String type, String url, String location, LocalDate startDate, LocalDate endDate) {
        Alert alert = null;
        if (title == null || title.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a Title");
        }
        if (description == null || description.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a Description");
        }
        if (type == null || type.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a type");
        }
        if (location == null || location.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a Location");
        }
        if (startDate == null) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a Start Date");
        }
        if (endDate == null) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a End Date");
        }
        if (alert != null) {
            alert.show();
            return false;
        }
        return true;
    }

    public void goBack() throws Exception {
        Main.goToAppointment();
    }
}