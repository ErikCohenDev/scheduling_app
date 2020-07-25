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

    public static void init() {
        customers = DBInstance.getInstance().getAllCustomers();
        addresses = DBInstance.getInstance().getAllAddresses();
        cities = DBInstance.getInstance().getAllCities();
        countries = DBInstance.getInstance().getAllCountries();
        appointments = DBInstance.getInstance().getAllAppointments();
    }
}