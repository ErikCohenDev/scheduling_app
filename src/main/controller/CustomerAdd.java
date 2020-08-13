package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextInputControl;
import main.Main;
import main.db.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

class CustomerAddInvalidFormException extends Exception
{
    public CustomerAddInvalidFormException(String message)
    {
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.show();
    }
}

public class CustomerAdd implements Initializable {
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

    public void add() throws Exception {
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        String address = addressInput.getText();
        String address2 = address2Input.getText();
        String city = cityInput.getText();
        String zip = zipInput.getText();
        String country = countryInput.getText();
        boolean active = activeCheckbox.isSelected();

        try {
            this.validateForm(name, phone, address, city, zip, country);
        } catch(CustomerAddInvalidFormException e){
            return;
        }

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

    private void validateForm(String name, String phone, String address, String city, String zip, String country) throws CustomerAddInvalidFormException {
        if (name == null || name.trim().equals("")) {
            throw new CustomerAddInvalidFormException("Name is a required field");
        }
        if (phone == null || phone.trim().equals("")) {
            throw new CustomerAddInvalidFormException("Phone is a required field");
        }
        if (address == null || address.trim().equals("")) {
            throw new CustomerAddInvalidFormException("Address is a required field");
        }
        if (city == null || city.trim().equals("")) {
            throw new CustomerAddInvalidFormException("City is a required field");
        }
        if (zip == null || zip.trim().equals("")) {
            throw new CustomerAddInvalidFormException("Zip is a required field");
        }
        if (country == null || country.trim().equals("")) {
            throw new CustomerAddInvalidFormException("Country is a required field");
        }
    }

    public void goBack() throws Exception {
        Main.goToCustomer();
    }
}