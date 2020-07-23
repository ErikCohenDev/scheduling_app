package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import main.db.DBInstance;
import main.model.AppointmentModel;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class Appointment implements Initializable {
    @FXML
    private TableView<AppointmentModel> appointmentTable;
    @FXML
    private TableColumn<AppointmentModel, Integer> appointmentIdCol;
    @FXML
    private TableColumn<AppointmentModel, String> appointmentTitleCol;
    @FXML
    private TableColumn<AppointmentModel, String> appointmentDescriptionCol;
    @FXML
    private TableColumn<AppointmentModel, String> appointmentLocationCol;
    @FXML
    private TableColumn<AppointmentModel, String> appointmentContactCol;
    @FXML
    private TableColumn<AppointmentModel, String> appointmentTypeCol;
    @FXML
    private TableColumn<AppointmentModel, String> appointmentURLCol;
    @FXML
    private TableColumn<AppointmentModel, LocalDateTime> appointmentStartCol;
    @FXML
    private TableColumn<AppointmentModel, LocalDateTime> appointmentEndCol;
 
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<AppointmentModel> appointments = DBInstance.getInstance().getAllAppointments();
        ObservableList<AppointmentModel> observableAppointments = FXCollections.observableArrayList(appointments);
        this.appointmentTable.setItems(observableAppointments);
        this.appointmentIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.appointmentTitleCol.setCellValueFactory(new PropertyValueFactory("title"));
        this.appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        this.appointmentLocationCol.setCellValueFactory(new PropertyValueFactory("location"));
        this.appointmentContactCol.setCellValueFactory(new PropertyValueFactory("contact"));
        this.appointmentTypeCol.setCellValueFactory(new PropertyValueFactory("type"));
        this.appointmentURLCol.setCellValueFactory(new PropertyValueFactory("url"));
        this.appointmentStartCol.setCellValueFactory(new PropertyValueFactory("start"));
        this.appointmentEndCol.setCellValueFactory(new PropertyValueFactory("end"));
    }

    public void goBack() throws Exception {
        Main.goToDashboard();
    }
}
