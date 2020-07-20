package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.controller.AppointmentModify;
import main.controller.CustomerModify;
import main.db.DBInstance;
import main.model.Appointment;
import main.model.Customer;
import main.model.LocaleInstance;

public class Main extends Application {
    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        mainStage = primaryStage;
        DBInstance.getInstance();
        LocaleInstance.getInstance();
    }

    public static void goToLogin() throws Exception {
        final Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("view/Login.fxml")));
        mainStage.setScene(scene);
    }

    public static void goToDashboard() throws Exception {
        final Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("view/Dashboard.fxml")));
        mainStage.setScene(scene);
    }

    public static void goToAppointment() throws Exception {
        final Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("view/Appointment.fxml")));
        mainStage.setScene(scene);
    }

    public static void goToAppointmentAdd() throws Exception {
        final Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("view/AppointmentAdd.fxml")));
        mainStage.setScene(scene);
    }

    public static void goToAppointmentModify(Appointment appointment) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/AppointmentModify.fxml"));
        final Scene scene = new Scene(loader.load());
        AppointmentModify controller = loader.getController();
        controller.initData(appointment);
        mainStage.setScene(scene);
    }

    public static void goToCustomer() throws Exception {
        final Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("view/Customer.fxml")));
        mainStage.setScene(scene);
    }

    public static void goToCustomerAdd() throws Exception {
        final Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("view/CustomerAdd.fxml")));
        mainStage.setScene(scene);
    }

    public static void goToCustomerModify(Customer customer) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/CustomerModify.fxml"));
        final Scene scene = new Scene(loader.load());
        CustomerModify controller = loader.getController();
        controller.initData(customer);
        mainStage.setScene(scene);
    }

    public static void goToReport() throws Exception {
        final Scene scene = new Scene(FXMLLoader.load(Main.class.getResource("view/Report.fxml")));
        mainStage.setScene(scene);
    }

    public static void exit() {
        mainStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
