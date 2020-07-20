package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.db.Authenticate;
import main.model.LocaleInstance;
import main.model.User;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private Label username_label;
    @FXML
    private TextField username_input;
    @FXML
    private Label password_label;
    @FXML
    private TextField password_input;
    @FXML
    private Button login_button;

    private Properties dictionary = LocaleInstance.getInstance().getDictionary();


    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        username_label.setText(String.valueOf(dictionary.get("username")));
        password_label.setText(String.valueOf(dictionary.get("password")));
        login_button.setText(String.valueOf(dictionary.get("login")));
    }

    public void login(final ActionEvent event) throws Exception {
        String username = username_input.getText();
        String password = password_input.getText();
        User loggedInUser = Authenticate.login(username, password);
        if (loggedInUser != null) {
            Main.goToDashboard();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, String.valueOf(dictionary.get("login_error")));
            alert.show();
        }
    }
}
