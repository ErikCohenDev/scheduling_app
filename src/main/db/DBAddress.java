package main.db;

import main.model.AddressModel;
import main.model.CityModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract public class DBAddress {
    public static List<AddressModel> getAll() {
        Connection conn = DBInstance.getInstance().getConnection();
        List<HashMap> addressesHash = DBInstance.getInstance().selectAll(conn, "address");
        if (addressesHash != null && addressesHash.size() > 0) {
            List<AddressModel> addresses = new ArrayList();
            System.out.println("Addresses: ");
            for (HashMap address: addressesHash) {
                System.out.println(address);
                int cityId = Integer.parseInt(address.get("cityId").toString());
                addresses.add(new AddressModel(
                                Integer.parseInt(address.get("addressId").toString()),
                                address.get("address").toString(),
                                address.get("address2").toString(),
                                Store.getCities().stream().filter(city -> city.getId() == cityId).findFirst().get(),
                                address.get("postalCode").toString(),
                                address.get("phone").toString()
                        )
                );
            }
            return addresses;
        } else {
            return null;
        }
    }

    public static int create(String address, String address2, int cityId, String zip, String phone, String currentDateTime, String user) throws SQLException {
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String insertStatement = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, lastUpdateBy, createdBy) " +
                "VALUES ('"+
                address +"', '" +
                address2 +"', '" +
                cityId +"', '" +
                zip +"', '" +
                phone +"', '" +
                currentDateTime +"', '" +
                user +"', '" +
                user +"');";
        System.out.println("executing query: " + insertStatement);
        int affectedRows = stmt.executeUpdate(insertStatement, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows == 0) {
            throw new SQLException("Creating address failed, no rows affected.");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating address failed, no ID obtained.");
            }
        }
    }

    public static void delete(AddressModel address) throws SQLException {
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String deleteStatement = "DELETE FROM address WHERE addressId = " + address.getId() + ";";
        System.out.println("executing query: " + deleteStatement);
        stmt.executeUpdate(deleteStatement);
        DBCity.delete(address.getCity());
    }
}
