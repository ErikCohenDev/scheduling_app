package main.model;

import main.db.Store;

import java.util.Optional;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private Optional<AddressModel> getAddressRefById(int addressId) {
        return Store.getAddresses().stream()
                .filter(address -> address.getId() == addressId)
                .findFirst();
    }

    public String getPhone() {
        Optional<AddressModel> addressRef = getAddressRefById(this.addressId);
        if (addressRef.isPresent()) {
            return addressRef.get().getPhone();
        }
        return null;
    }

    public String getAddress() {
        Optional<AddressModel> addressRef = getAddressRefById(this.addressId);
        if (addressRef.isPresent()) {
            return addressRef.get().getName();
        }
        return null;
    }

    public String getAddress2() {
        Optional<AddressModel> addressRef = getAddressRefById(this.addressId);
        if (addressRef.isPresent()) {
            return addressRef.get().getName2();
        }
        return null;
    }

    public String getCity() {
        Optional<AddressModel> addressRef = getAddressRefById(this.addressId);
        if (addressRef.isPresent()) {
            return addressRef.get().getCity();
        }
        return null;
    }

    public String getZip() {
        Optional<AddressModel> addressRef = getAddressRefById(this.addressId);
        if (addressRef.isPresent()) {
            return addressRef.get().getPostalCode();
        }
        return null;
    }

    public String getCountry() {
        Optional<AddressModel> addressRef = getAddressRefById(this.addressId);
        if (addressRef.isPresent()) {
            return addressRef.get().getCountry();
        }
        return null;
    }

    public boolean isActive() {
        return active;
    }
}