package main.controller;

import javafx.fxml.Initializable;
import main.Main;
import main.db.Authenticate;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void goToCustomerView() throws Exception {
        Main.goToCustomer();
    }

    public void goToAppointmentView() throws Exception {
        Main.goToCustomer();
    }

    public void gotToReportView() throws Exception {
        Main.goToReport();
    }

    public void logout() throws Exception {
        Authenticate.logout();
        Main.goToLogin();
    }
}