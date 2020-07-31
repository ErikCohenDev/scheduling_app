package main.model;

public class CityModel {
    private int id;
    private String name;
    private CountryModel country;

    public CityModel(int id, String name, CountryModel country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CountryModel getCountry() {
        return this.country;
    }
}
