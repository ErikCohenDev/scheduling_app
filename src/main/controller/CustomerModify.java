package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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
    private Label idLabel;
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
        idLabel.setText(String.valueOf(customer.getId()));
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
        int customerId = Integer.parseInt(idLabel.getText());
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();
        String address2 = address2Input.getText();
        String city = cityInput.getText();
        String zip = zipInput.getText();
        String country = countryInput.getText();
        boolean active = activeCheckbox.isSelected();

        CustomerModel customer = Store.getCustomers().stream()
                .filter(cust -> cust.getId() == customerId).findFirst().get();

        int countryId = customer.getCountry().getId();
        int cityId = customer.getCity().getId();
        int addressId = customer.getAddress().getId();

        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String utcLocalDateTime = customFormatter.format(utcZdt);
        String user = Authenticate.user.getUsername();

        String oldCountry = customer.getCountryName();
        if (!country.equals(oldCountry)) {
            DBCountry.update(countryId, country, utcLocalDateTime, user);
        }

        String oldCity = customer.getCityName();
        if (!city.equals(oldCity)) {
            DBCity.update(cityId, city, utcLocalDateTime, user);
        }

        String oldAddress = customer.getAddressName();
        String oldAddress2 = customer.getAddress2();
        String oldZip = customer.getZip();
        String oldPhone = customer.getPhone();

        if (!address.equals(oldAddress) || !address2.equals(oldAddress2) || !zip.equals(oldZip) || !phone.equals(oldPhone)) {
            DBAddress.update(addressId, address, address2, zip, phone, utcLocalDateTime, user);
        }

        String oldName = customer.getName();
        boolean oldActive = customer.getActive();
        if (!name.equals(oldName) || oldActive != active) {
            DBCustomer.update(customerId, name, active, utcLocalDateTime, user);
        }
        Store.refreshCustomer();
        Main.goToCustomer();
    }

    public void goBack() throws Exception {
        Main.goToCustomer();
    }
}