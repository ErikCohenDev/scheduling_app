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
        CustomerModel customer = (CustomerModel) customerChoiceBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        int startHour = Integer.parseInt(startHourInput.getText());
        int startMin = Integer.parseInt(startMinInput.getText());
        int endHour = Integer.parseInt(endHourInput.getText());
        int endMin = Integer.parseInt(endMinInput.getText());
        int startHour24 = startPeriodChoiceBox.getValue().equals("AM") ? startHour :  startHour + 12;
        int endHour24 = endPeriodChoiceBox.getValue().equals("AM") ? endHour :  endHour + 12;

        if (startDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please Enter a Start Date");
            alert.show();
            return;
        }

        LocalDateTime startDateTime = startDate.atTime(startHour24, startMin);
        LocalDateTime endDateTime = startDate.atTime(endHour24, endMin);

        if (!validForm(title, description, type, url, location, startDateTime, endDateTime)) {
            return;
        }

        UserModel user = Authenticate.user;
        DBAppointment.create(title, description, type, url, location, customer, startDateTime, endDateTime, user);
        Store.refreshAppointments();
        goBack();
    }

    private boolean validForm(String title, String description, String type, String url, String location, LocalDateTime startDate, LocalDateTime endDate) {
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

        if(startDate.getHour() > endDate.getHour()) {
            alert = new Alert(Alert.AlertType.ERROR, "Start Time must be before End Time");
        }
        if (startDate.getHour() < 8) {
            alert = new Alert(Alert.AlertType.ERROR, "Start Time must be during normal business hours, Schedule your start time after 8AM");
        }
        if ((endDate.getHour() == 17 && endDate.getMinute() > 0) || endDate.getHour() > 17) {
            alert = new Alert(Alert.AlertType.ERROR, "End Time must be during normal business hours, Schedule your end time before 5PM");
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
