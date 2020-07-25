package main.model;

import main.db.Store;

import java.util.Optional;

public class AddressModel {
    private int id;
    private String name;
    private String name2;
    private int cityId;
    private String postalCode;
    private String phone;

    public AddressModel(int id, String name, String name2, int cityId, String postalCode, String phone) {
        this.id = id;
        this.name = name;
        this.name2 = name2;
        this.cityId = cityId;
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

    public int getCityId() {
        return cityId;
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

    public String getCity() {
        Optional<CityModel> cityRef = getCityRefById(cityId);
        if (cityRef.isPresent()) {
            return cityRef.get().getName();
        }
        return null;
    }

    public String getCountry() {
        Optional<CityModel> cityRef = getCityRefById(cityId);
        if (cityRef.isPresent()) {
            return cityRef.get().getCountryName();
        }
        return null;
    }
}
