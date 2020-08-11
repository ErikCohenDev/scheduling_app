package main.model;

import main.db.Store;

import java.time.format.TextStyle;
import java.util.Locale;

public class UserModel {
    private int id;
    private String username;
    private boolean active;

    public UserModel(int id, String username, boolean active) {
        this.id = id;
        this.username = username;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public int allAppointmentCountByMonth(int userId, String month) {
        return (int) Store.getAllAppointments().stream()
                .filter(appointment -> userId == appointment.getUser().id && appointment.getStartDate().getMonth().getDisplayName(TextStyle.FULL, Locale.US).equals(month))
                .count();
    }

    public String getUsername() {
        return username;
    }

    public boolean isActive() {
        return active;
    }
}