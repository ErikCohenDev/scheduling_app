package main.model;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private boolean active;

    public User(int id, String username, boolean active) {
        this.id = id;
        this.username = username;
        this.active = active;
    }
}
