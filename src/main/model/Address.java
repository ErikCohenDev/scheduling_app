package main.model;

public class Address {
    private int id;
    private String name;
    private String name2;
    private int cityId;
    private String postalCode;
    private String phone;

    public Address(int id, String name, String name2, int cityId, String postalCode, String phone) {
        this.id = id;
        this.name = name;
        this.name2 = name2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
    }
}
