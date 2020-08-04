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
import main.model.CustomerModel;
import main.model.UserModel;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        customerChoiceBox.setValue(customerList.stream().findFirst().get());
        endPeriodChoiceBox.getItems().addAll("AM", "PM");
        startPeriodChoiceBox.getItems().addAll("AM", "PM");
        startPeriodChoiceBox.setValue("AM");
        endPeriodChoiceBox.setValue("AM");
    }
    public void add() throws Exception {
        String title = titleInput.getText();
        String description = descriptionInput.getText();
        String type = typeInput.getText();
        String url = urlInput.getText();
        String location = locationInput.getText();
        String country = countryInput.getText();
        CustomerModel customer = (CustomerModel) customerChoiceBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        int startHour = Integer.parseInt(startHourInput.getText());
        int startMin = Integer.parseInt(startMinInput.getText());
        LocalDate endDate = endDatePicker.getValue();
        int endHour = Integer.parseInt(endHourInput.getText());
        int endMin = Integer.parseInt(endMinInput.getText());

        if (!validForm(title, description, type, url, location, country, startDate, endDate)) {
            return;
        }

        LocalDateTime startDateTime = startDate.atTime(startHour, startMin);
        LocalDateTime endDateTime = endDate.atTime(endHour, endMin);

        UserModel user = Authenticate.user;
        DBAppointment.create(title, description, type, url, location, country, customer, startDateTime, endDateTime, user);
        Store.refreshAppointments();
        goBack();
    }

    private boolean validForm(String title, String description, String type, String url, String location, String country, LocalDate startDate, LocalDate endDate) {
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
        if (url == null || url.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a URL");
        }
        if (location == null || location.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a Location");
        }
        if (country == null || country.equals("")) {
            alert = new Alert(Alert.AlertType.ERROR, "Please Enter a Country");
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
