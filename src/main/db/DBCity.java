package main.db;

import main.model.CityModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract public class DBCity {
    public static List<CityModel> getAll() {
        Connection conn = DBInstance.getInstance().getConnection();
        List<HashMap> citiesHash = DBInstance.getInstance().selectAll(conn, "city");
        if (citiesHash != null && citiesHash.size() > 0) {
            List<CityModel> cities = new ArrayList();
            System.out.println("Cities: ");
            for (HashMap city: citiesHash) {
                System.out.println(city);
                int countryId = Integer.parseInt(city.get("countryId").toString());
                cities.add(new CityModel(
                        Integer.parseInt(city.get("cityId").toString()),
                        city.get("city").toString(),
                        Store.getCountries().stream().filter(country -> country.getId() == countryId).findFirst().get()
                    )
                );
            }
            return cities;
        } else {
            return null;
        }
    }

    public static int create(String name, int countryId, String date, String user) throws SQLException{
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String insertStatement = "INSERT INTO city(city, countryId, createDate, lastUpdateBy, createdBy) " +
                "VALUES ('"+
                name +"', '" +
                countryId +"', '" +
                date +"', '" +
                user +"', '" +
                user +"');";
        System.out.println("executing query: " + insertStatement);
        int affectedRows = stmt.executeUpdate(insertStatement, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    public static void update(int id, String name, String utcLocalDateTime, String user) throws SQLException{
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String updateStatement = "UPDATE city " +
                "set city = '" + name +"' , " +
                "lastUpdate = '" + utcLocalDateTime +"' ," +
                "lastUpdateBy = '" + user +"' " +
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

    public static void delete(CityModel city) throws SQLException {
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String deleteStatement = "DELETE FROM city WHERE cityId = " + city.getId() + ";";
        System.out.println("executing query: " + deleteStatement);
        stmt.executeUpdate(deleteStatement);
        DBCountry.delete(city.getCountry());
    }
}
