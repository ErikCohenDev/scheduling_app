package main.db;

import main.model.AppointmentModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract public class DBAppointment {
    public static List<AppointmentModel> getAll() {
        Connection conn = DBInstance.getInstance().getConnection();
        List<HashMap> appointmentsHash = DBInstance.getInstance().selectAll(conn, "appointment");
        if (appointmentsHash != null && appointmentsHash.size() > 0) {
            List<AppointmentModel> appointments = new ArrayList();
            System.out.println("appointments: ");
            for (HashMap appointment: appointmentsHash) {
                System.out.println(appointment);
                appointments.add(new AppointmentModel(
                                Integer.parseInt(appointment.get("appointmentId").toString()),
                                appointment.get("title").toString(),
                                appointment.get("description").toString(),
                                appointment.get("location").toString(),
                                appointment.get("contact").toString(),
                                appointment.get("type").toString(),
                                appointment.get("url").toString(),
                                ((Timestamp)appointment.get("start")).toLocalDateTime(),
                                ((Timestamp)appointment.get("end")).toLocalDateTime(),
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

    public void create(String name, int countryId, String date, String user) throws SQLException{
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String insertStatement = "INSERT INTO appointment(city, countryId, createDate, createdBy, lastUpdateBy) " +
                "VALUES ('"+
                name +"', '" +
                countryId +"', '" +
                date +"', '" +
                user +"', '" +
                user +"');";
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
