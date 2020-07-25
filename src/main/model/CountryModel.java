package main.model;

public class CountryModel {
    private int id;
    private String name;

    public CountryModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
