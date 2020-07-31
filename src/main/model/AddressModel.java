package main.model;

import main.db.Store;

import java.util.Optional;

public class AddressModel {
    private int id;
    private String name;
    private String name2;
    private CityModel city;
    private String postalCode;
    private String phone;

    public AddressModel(int id, String name, String name2, CityModel city, String postalCode, String phone) {
        this.id = id;
        this.name = name;
        this.name2 = name2;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getName2() {
        return name2;
    }

    public CityModel city() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    private Optional<CityModel> getCityRefById(int cityId) {
        return Store.getCities().stream()
                .filter(city -> city.getId() == cityId)
                .findFirst();
    }

    public CityModel getCity() {
        return city;
    }

    public CountryModel getCountry() {
        return city.getCountry();
    }
}
