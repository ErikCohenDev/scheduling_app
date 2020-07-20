package main.controller;

import javafx.fxml.Initializable;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class Report implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void goBack() throws Exception {
        Main.goToDashboard();
    }
}