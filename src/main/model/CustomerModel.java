package main.model;

public class CustomerModel {
    private int id;
    private String name;
    private AddressModel address;
    private boolean active;

    public CustomerModel(int id, String name, AddressModel address, boolean active) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getAddressName() {
        return address.getName();
    }

    public String getPhone() {
        return address.getPhone();
    }

    public String getAddress2() {
        return address.getName2();
    }

    public String getZip() {
        return address.getPostalCode();
    }

    public String getCityName() {
        return address.getCity().getName();
    }

    public String getCountryName() {
        return address.getCountry().getName();
    }

    public String getName() {
        return name;
    }

    public AddressModel getAddress() {
        return address;
    }

    public CityModel getCity() {
        return address.getCity();
    }

    public CountryModel getCountry() {
        return address.getCountry();
    }

    public boolean getActive() {
        return active;
    }
}