package main.db;

import main.model.AppointmentModel;
import main.model.CustomerModel;
import main.model.UserModel;
import main.util.DateUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

abstract public class DBAppointment {
    public static List<AppointmentModel> getAll() {
        Connection conn = DBInstance.getInstance().getConnection();
        List<HashMap> appointmentsHash = DBInstance.getInstance().selectAll(conn, "appointment");
        if (appointmentsHash != null && appointmentsHash.size() > 0) {
            List<AppointmentModel> appointments = new ArrayList();
            System.out.println("appointments: ");
            for (HashMap appointment: appointmentsHash) {
                String startDateString = String.valueOf(appointment.get("start"));
                String endDateString = String.valueOf(appointment.get("end"));

                ZonedDateTime startDate = DateUtils.UTCStringToZonedDateTime(startDateString);
                ZonedDateTime endDate = DateUtils.UTCStringToZonedDateTime(endDateString);

                appointments.add(new AppointmentModel(
                                Integer.parseInt(appointment.get("appointmentId").toString()),
                                appointment.get("title").toString(),
                                appointment.get("description").toString(),
                                appointment.get("location").toString(),
                                appointment.get("contact").toString(),
                                appointment.get("type").toString(),
                                appointment.get("url").toString(),
                                startDate,
                                endDate,
                                Integer.parseInt(appointment.get("customerId").toString()),
                                Integer.parseInt(appointment.get("userId").toString())
                        )
                );
            }
            return appointments;
        } else {
            return null;
        }
    }

    private static ZonedDateTime convertUTCToLocal(String dt) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime dateTime = LocalDateTime.parse(dt, formatter);
        ZonedDateTime zonedDateTime = dateTime.atZone(zone);

        return zonedDateTime;
    }

    public static void create(String title, String description, String type, String url, String location, String country, CustomerModel customer, LocalDateTime startDateTime, LocalDateTime endDateTime, UserModel user) throws SQLException {
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime locZdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utcZdt = locZdt.withZoneSameInstant(ZoneOffset.UTC);
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String utcLocalDateTime = customFormatter.format(utcZdt);

        ZonedDateTime startLocZdt = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
        ZonedDateTime endLocZdt = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());

        ZonedDateTime utcStartZdt = startLocZdt.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime utcEndZdt = endLocZdt.withZoneSameInstant(ZoneOffset.UTC);
        String utcStartLocalDateTime = customFormatter.format(utcStartZdt);
        String utcEndLocalDateTime = customFormatter.format(utcEndZdt);

        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String insertStatement = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) " +
                "VALUES ("+
                customer.getId() +", " +
                user.getId() +", '" +
                title +"', '" +
                description +"', '" +
                location +"', '" +
                customer.getPhone() +"', '" +
                type +"', '" +
                url +"', '" +
                utcStartLocalDateTime +"', '" +
                utcEndLocalDateTime +"', '" +
                utcLocalDateTime +"', '" +
                user.getUsername() +"', '" +
                utcLocalDateTime +"', '" +
                user.getUsername() +"');";
        System.out.println("executing query: " + insertStatement);
        stmt.executeUpdate(insertStatement, Statement.RETURN_GENERATED_KEYS);

        if (stmt.getUpdateCount() > 0) {
            System.out.println(stmt.getUpdateCount() + " rows created");
        } else {
            System.out.println("No change!");
        }
    }

    public void update(int id, String name, String user) throws SQLException{
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String updateStatement = "UPDATE appointment " +
                "set city = '" + name +"' , " +
                "lastupdateBy = '" + user +"' " +
                "WHERE cityId = " +
                id + ";";
        System.out.println("executing query: " + updateStatement);
        stmt.executeUpdate(updateStatement);

        if (stmt.getUpdateCount() > 0) {
            System.out.println(stmt.getUpdateCount() + " rows updated");
        } else {
            System.out.println("No change!");
        }
    }

    public static void delete(AppointmentModel selectedAppointment) {
    }
}
