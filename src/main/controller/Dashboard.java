package main.controller;

import main.Main;
import main.db.Authenticate;

public class Dashboard {
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