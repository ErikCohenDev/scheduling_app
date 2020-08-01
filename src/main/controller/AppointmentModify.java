package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextInputControl;
import main.model.AppointmentModel;

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

    }

    public void initData(AppointmentModel appointment) {
    }

    public void update() {

    }

    public void goBack() {

    }
}