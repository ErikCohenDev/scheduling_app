package main.model;

import main.db.Store;

import java.util.Optional;

public class CityModel {
    private int id;
    private String name;
    private int countryId;

    public CityModel(int id, String name, int countryId) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private Optional<CountryModel> getCountryRefById(int countryId) {
        return Store.getCountries().stream()
                .filter(country -> country.getId() == countryId)
                .findFirst();
    }

    public String getCountryName() {
        Optional<CountryModel> countryRef = getCountryRefById(this.countryId);
        if (countryRef.isPresent()) {
            return countryRef.get().getName();
        }
        return null;
    }
}
