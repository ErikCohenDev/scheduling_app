package main.model;

public class CustomerModel {
    private int id;
    private String name;
    private int addressId;
    private boolean active;

    public CustomerModel(int id, String name, int addressId, boolean active) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.active = active;
    }
}