package main.controller;

import javafx.fxml.Initializable;
import main.Main;
import main.db.DBInstance;
import main.model.AddressModel;
import main.model.CityModel;
import main.model.CountryModel;
import main.model.CustomerModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Customer implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<CustomerModel> customers = DBInstance.getInstance().getAllCustomers();
        List<AddressModel> addresses = DBInstance.getInstance().getAllAddresses();
        List<CityModel> cities = DBInstance.getInstance().getAllCities();
        List<CountryModel> countries = DBInstance.getInstance().getAllCountries();
    }

    public void goBack() throws Exception {
        Main.goToDashboard();
    }
}