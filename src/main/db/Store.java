package main.db;

import main.model.*;

import java.util.List;

public class Store {
    private static List<CustomerModel> customers;
    private static List<AppointmentModel> appointments;
    private static List<AddressModel> addresses;
    private static List<CityModel> cities;
    private static List<CountryModel> countries;

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

    public static void init() {
        countries = DBCountry.getAll();
        cities = DBCity.getAll();
        addresses = DBAddress.getAll();
        customers = DBCustomer.getAll();
        appointments = DBAppointment.getAll();
    }

    public static void refreshAppointments() {
        appointments = DBAppointment.getAll();
    }
}