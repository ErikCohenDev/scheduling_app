package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputControl;
import main.Main;
import main.db.*;
import main.model.AddressModel;
import main.model.CustomerModel;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CustomerModify implements Initializable {
    @FXML
    private TextInputControl nameInput;
    @FXML
    private TextInputControl phoneInput;
    @FXML
    private TextInputControl addressInput;
    @FXML
    private TextInputControl address2Input;
    @FXML
    private TextInputControl cityInput;
    @FXML
    private TextInputControl zipInput;
    @FXML
    private TextInputControl countryInput;
    @FXML
    private CheckBox activeCheckbox;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activeCheckbox.setSelected(true);
    }

    public void initData(final CustomerModel customer) {
        nameInput.setText(customer.getName());
        activeCheckbox.setSelected(customer.getActive());

        AddressModel address = customer.getAddress();
        addressInput.setText(address.getName());
        address2Input.setText(address.getName2());
        phoneInput.setText(customer.getAddress().getPhone());
        cityInput.setText(address.getCity().getName());
        zipInput.setText(address.getPostalCode());
        countryInput.setText(address.getCountry().getName());
    }

    public void update() throws Exception {
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();
        String address2 = address2Input.getText();
        String city = cityInput.getText();
        String zip = zipInput.getText();
        String country = countryInput.getText();
        boolean active = activeCheckbox.isSelected();

        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String utcLocalDateTime = customFormatter.format(utcZdt);
        String user = Authenticate.user.getUsername();
        int isActive = active ? 1 : 0;
        int countryId = DBCountry.create(country, utcLocalDateTime, user);
        int cityId = DBCity.create(city, countryId, utcLocalDateTime, user);
        int addressId = DBAddress.create(address, address2, cityId, zip, phone, utcLocalDateTime, user);
        DBCustomer.create(name, addressId, isActive, utcLocalDateTime, user);
        Store.refreshCustomer();
        Main.goToCustomer();
    }

    public void goBack() throws Exception {
        Main.goToCustomer();
    }
}