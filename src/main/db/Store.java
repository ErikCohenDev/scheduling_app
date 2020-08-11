package main.db;

import main.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class Store {
    private static List<CustomerModel> customers;
    private static List<AppointmentModel> appointments;
    private static List<AppointmentModel> allAppointments;
    private static List<AddressModel> addresses;
    private static List<CityModel> cities;
    private static List<CountryModel> countries;
    private static List<UserModel> users;

    public static List<UserModel> getUsers() {
        return users;
    }

    public static List<CustomerModel> getCustomers() {
        return customers;
    }

    public static List<AppointmentModel> getAppointments() {
        return appointments;
    }

    public static List<AddressModel> getAddresses() {
        return addresses;
    }

    public static List<CityModel> getCities() {
        return cities;
    }

    public static List<CountryModel> getCountries() {
        return countries;
    }

    public static void refreshCustomer() {
        countries = DBCountry.getAll();
        cities = DBCity.getAll();
        addresses = DBAddress.getAll();
        customers = DBCustomer.getAll();
    }

    public static List<String> getAllAppointmentTypes() {
        if (appointments == null) {
            return null;
        }
        return appointments.stream().map(appointment -> appointment.getType()).collect(Collectors.toList());
    }

    public static List<AppointmentModel> getAllAppointments() {
        return allAppointments;
    }

    public static void init() {
        countries = DBCountry.getAll();
        cities = DBCity.getAll();
        addresses = DBAddress.getAll();
        customers = DBCustomer.getAll();
        users = DBUser.getAll();
        appointments = DBAppointment.getAll();
        allAppointments = DBAppointment.getAllFromAllUsers();
    }

    public static void refreshAppointments() {
        appointments = DBAppointment.getAll();
    }
}