package main.model;

public class Customer {
    private int id;
    private String name;
    private int addressId;
    private boolean active;

    public Customer(int id, String name, int addressId, boolean active) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.active = active;
    }
}
