package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.Main;
import main.db.Authenticate;
import main.db.Store;
import main.model.AppointmentModel;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @FXML
    private Label notificationLabel;
    @FXML
    private Pane notificationBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notificationBox.setVisible(false);
        Store.refreshAppointments();
        //Chose to use a lambda expression here to quickly get the next appointment instead of creating an encapsulating class
        if (Store.getAppointments() != null) {
            Optional<AppointmentModel> nextAppointmentOptional = Store.getAppointments().stream().filter(appointment -> appointment.isWithin15Mins()).findFirst();

            if (nextAppointmentOptional.isPresent()) {
                AppointmentModel nextAppointment = nextAppointmentOptional.get();
                String startTime = nextAppointment.getStartDate().format(DateTimeFormatter.ofPattern("hh:mm a"));
                String customer = nextAppointment.getContactObject().getName();
                notificationLabel.setText("You have an upcoming appointment with " + customer + " at " + startTime);
                notificationBox.setVisible(true);
            }
        }
    }

    public void goToCustomerView() throws Exception {
        Main.goToCustomer();
    }

    public void goToAppointmentView() throws Exception {
        Main.goToAppointment();
    }

    public void gotToReportView() throws Exception {
        Main.goToReport();
    }

    public void logout() throws Exception {
        Authenticate.logout();
        Main.goToLogin();
    }
}