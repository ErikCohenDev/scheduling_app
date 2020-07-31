package main.db;

import main.model.CustomerModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

abstract public class DBCustomer {
    public static List<CustomerModel> getAll() {
        Connection conn = DBInstance.getInstance().getConnection();
        List<HashMap> customersHash = DBInstance.getInstance().selectAll(conn, "customer");
        if (customersHash != null && customersHash.size() > 0) {
            List<CustomerModel> customers = new ArrayList();
            System.out.println("Customers: ");
            for (HashMap customer: customersHash) {
                System.out.println(customer);
                customers.add(new CustomerModel(
                                Integer.parseInt(customer.get("customerId").toString()),
                                customer.get("customerName").toString(),
                                Integer.parseInt(customer.get("addressId").toString()),
                                customer.get("active").equals("true")
                        )
                );
            }
            return customers;
        } else {
            return null;
        }
    }

    public static int create(String name, int addressId, int active, String currentDateTime, String user) throws SQLException {
        Connection conn = DBInstance.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        String insertStatement = "INSERT INTO customer(customerName, addressId, active, createDate, lastUpdateBy, createdBy) " +
                "VALUES ('"+
                name +"', '" +
                addressId +"', '" +
                active +"', '" +
                currentDateTime +"', '" +
                currentDateTime +"', '" +
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
}
