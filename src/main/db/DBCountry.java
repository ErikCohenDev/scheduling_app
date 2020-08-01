package main.db;

import main.model.CountryModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract public class DBCountry {
    public static List<CountryModel> getAll() {
        Connection conn = DBInstance.getInstance().getConnection();
        List<HashMap> countriesHash = DBInstance.getInstance().selectAll(conn, "country");
        if (countriesHash != null && countriesHash.size() > 0) {
            List<CountryModel> countries = new ArrayList();
            System.out.println("Countries: ");
            for (HashMap country: countriesHash) {
                System.out.println(country);
                countries.add(new CountryModel(
                                Integer.parseInt(country.get("countryId").toString()),
                                country.get("country").toString()
                        )
                );
            }
            return countries;
        } else {
            return null;
        }
    }

    public static int create(String name, String currentDateTime, String user) throws SQLException{
        Connection conn = DBInstance.getInstance().getConnection();
        String insertStatement = "INSERT INTO country(country, createDate, lastUpdateBy, createdBy) " +
                "VALUES ('"+
                name +"', '" +
                currentDateTime +"', '" +
                user +"', '" +
                user +"');";
        PreparedStatement stmt = conn.prepareStatement(insertStatement);
        System.out.println("executing query: " + insertStatement);
        int affectedRows = stmt.executeUpdate(insertStatement, PreparedStatement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating country failed, no rows affected.");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating country failed, no ID obtained.");
            }
        }
    }

    public static void update(int countryId, String countryName, String utcLocalDateTime, String user) throws SQLException {
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String updateStatement = "UPDATE country " +
                "set country = '" + countryName +"' , " +
                "lastUpdate = '" + utcLocalDateTime +"' ," +
                "lastUpdateBy = '" + user +"' " +
                "WHERE countryId = " +
                countryId + ";";
        System.out.println("executing query: " + updateStatement);
        stmt.executeUpdate(updateStatement);
    }

    public static void delete(CountryModel country) throws SQLException {
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String deleteStatement = "DELETE FROM country WHERE countryId = " + country.getId() + ";";
        System.out.println("executing query: " + deleteStatement);
        stmt.executeUpdate(deleteStatement);
        Store.refreshCustomer();
    }

}
