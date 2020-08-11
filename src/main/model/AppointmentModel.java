package main.model;

import main.db.Store;
import main.util.DateUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class AppointmentModel {
    private int id;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private int customerId;
    private int userId;
    private UserModel user;

    public AppointmentModel(int id, String title, String description, String location, String contact, String type, String url, ZonedDateTime start, ZonedDateTime end, int customerId, int userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.user = Store.getUsers().stream().filter(user -> user.getId() == userId).findFirst().get();
    }

    public UserModel getUser() {
        return user;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return this.contact;
    }

    public CustomerModel getContactObject() {
        return Store.getCustomers().stream().filter(contact -> contact.getId() == customerId).findFirst().get();
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ZonedDateTime getStartDate() {
        return this.start;
    }

    public String getStart() {
        return DateUtils.zonedDateTimeToString(this.start);
    }

    public ZonedDateTime getEndDate() {
        return this.end;
    }

    public String getEnd() {
        return DateUtils.zonedDateTimeToString(this.end);
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public boolean isWithin15Mins() {
        ZonedDateTime now = ZonedDateTime.now();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        ZonedDateTime min15FromNow = ZonedDateTime.from(calendar.toInstant().atZone(ZoneId.systemDefault()));
        return this.start.isAfter(now) && this.start.isBefore(min15FromNow);// (now);
    }
}
