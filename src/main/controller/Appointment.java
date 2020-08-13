package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import main.db.DBAppointment;
import main.db.Store;
import main.model.AppointmentModel;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Appointment implements Initializable {
    ObservableList<AppointmentModel> observableAppointments;
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
    @FXML
    private RadioButton byMonthRadio;
    @FXML
    private RadioButton byWeekRadio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Store.refreshAppointments();
        final ToggleGroup group = new ToggleGroup();
        byMonthRadio.setToggleGroup(group);
        byWeekRadio.setToggleGroup(group);
        byMonthRadio.setSelected(true);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1)
            {
                RadioButton selectedToggle = (RadioButton)t1.getToggleGroup().getSelectedToggle();
                if (selectedToggle.getText().equals("Week")) {
                    observableAppointments = FXCollections.observableArrayList(getAppointmentsForTheWeek());
                } else {
                    observableAppointments = FXCollections.observableArrayList(getAppointmentsForTheMonth());
                }
                appointmentTable.setItems(observableAppointments);
            }
        });
        List<AppointmentModel> appointmentsForTheMonth = getAppointmentsForTheMonth();
        if (appointmentsForTheMonth != null) {
            observableAppointments = FXCollections.observableArrayList(appointmentsForTheMonth);
        }
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

    private List<AppointmentModel> getAppointmentsForTheMonth() {
        //  Chose to use a lambda expression here to quickly filter appointments instead of a traditional loop seems more readable.
        List<AppointmentModel> appointments = Store.getAppointments();
        if (appointments == null) {
            return new ArrayList<>();
        }
        return appointments.stream()
                .filter(appointment -> appointment.getStartDate().getMonth() == LocalDate.now().getMonth())
                .collect(Collectors.toList());
    }

    private List<AppointmentModel> getAppointmentsForTheWeek() {
        List<AppointmentModel> appointments = Store.getAppointments();
        if (appointments == null) {
            return new ArrayList<>();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_MONTH, 1);
        ZonedDateTime aWeekFromToday = ZonedDateTime.from(calendar.toInstant().atZone(ZoneId.systemDefault()));
        //  Chose to use a lambda expression here to quickly filter appointments instead of a traditional loop seems more readable.
        return appointments.stream()
                .filter(appointment -> appointment.getStartDate().isBefore(ChronoZonedDateTime.from(aWeekFromToday)) && appointment.getStartDate().isAfter(ZonedDateTime.now()))
                .collect(Collectors.toList());
    }

    public void add() throws Exception {
        Main.goToAppointmentAdd();
    }

    public void update() throws Exception {
        final AppointmentModel selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf("Please Select an Appointment"));
            alert.show();
            return;
        }
        Main.goToAppointmentModify(selectedAppointment);
    }

    public void delete() throws Exception {
        final AppointmentModel selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf("Please Select an Appointment"));
            alert.show();
            return;
        }
        DBAppointment.delete(selectedAppointment);
        Store.refreshAppointments();
        initialize(null, null);
    }

    public void goBack() throws Exception {
        Main.goToDashboard();
    }
}
