package main.model;

public class UserModel {
    private int id;
    private String username;
    private boolean active;

    public UserModel(int id, String username, boolean active) {
        this.id = id;
        this.username = username;
        this.active = active;
    }
}