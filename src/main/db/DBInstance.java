package main.db;

import main.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DBInstance {
    private static Connection conn = null;
    private static final DBInstance instance = new DBInstance();
    private Properties dbSecrets = new Properties();

    private DBInstance() {
        getDBSecrets();
    }


    public static DBInstance getInstance(){
        return instance;
    }

    private void getDBSecrets() {
        String secretsFile = "secrets.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(secretsFile);
        if (inputStream != null) {
            try {
                dbSecrets.load(inputStream);
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("There was a problem loading the DBSecrets property file");
        }
    }

    private Connection getConnection() {
        try {
            Class.forName(dbSecrets.getProperty("JDBC_DRIVER"));
            conn = DriverManager.getConnection(
                    dbSecrets.getProperty("DB_URL"),
                    dbSecrets.getProperty("DB_USER"),
                    dbSecrets.getProperty("DB_PASS")
            );
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    private List<HashMap> selectAll(Connection conn, String table) {
        ArrayList resultList = null;

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + ";");
            if (rs.next()) {
                resultList = this.convertsResultToMap(rs);
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return resultList;
    }

    private List<HashMap> selectAndFilter(Connection conn, String table, String filterLabel, String filterValue) {
        List<HashMap> resultList = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table + " WHERE " + filterLabel + "='" + filterValue + "';");
            if (rs.next() != false) {
                resultList = convertsResultToMap(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    private ArrayList<HashMap> convertsResultToMap(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        int rowCount = rs.last() ? rs.getRow() : 0;
        ArrayList list = new ArrayList(rowCount);
         do {
            HashMap row = new HashMap(columns);
            for(int i=1; i<=columns; ++i){
                row.put(md.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
        } while (rs.next());
        return list;
    }

    public HashMap getUserByUserName(String userName) {
        Connection conn = getConnection();

        List<HashMap> userData = selectAndFilter(conn, "user", "userName", userName);
        if (userData != null && userData.size() > 0) {
            return userData.get(0);
        }
        return null;
    }

    public List<CustomerModel> getAllCustomers() {
        Connection conn = this.getConnection();
        List<HashMap> customersHash = this.selectAll(conn, "customer");
        if (customersHash != null && customersHash.size() > 0) {
            List<CustomerModel> customers = new ArrayList();
            System.out.println("Customers: ");
            for (HashMap customer: customersHash) {
                System.out.println(customer);
                customers.add(new CustomerModel(
                                Integer.parseInt(customer.get("customerId").toString()),
                                customer.get("customerName").toString(),
                                Integer.parseInt(customer.get("addressId").toString()),
                                customer.get("active").equals("true") ? true : false
                        )
                );
            }
            return customers;
        } else {
            return null;
        }
    }

    public List<AddressModel> getAllAddresses() {
        Connection conn = this.getConnection();
        List<HashMap> addressesHash = this.selectAll(conn, "address");
        if (addressesHash != null && addressesHash.size() > 0) {
            List<AddressModel> addresses = new ArrayList();
            System.out.println("Addresses: ");
            for (HashMap address: addressesHash) {
                System.out.println(address);
                addresses.add(new AddressModel(
                            Integer.parseInt(address.get("addressId").toString()),
                            address.get("address").toString(),
                            address.get("address2").toString(),
                            Integer.parseInt(address.get("cityId").toString()),
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

    public List<CityModel> getAllCities() {
        Connection conn = this.getConnection();
        List<HashMap> citiesHash = this.selectAll(conn, "city");
        if (citiesHash != null && citiesHash.size() > 0) {
            List<CityModel> cities = new ArrayList();
            System.out.println("Cities: ");
            for (HashMap city: citiesHash) {
                System.out.println(city);
                cities.add(new CityModel(
                        Integer.parseInt(city.get("cityId").toString()),
                        city.get("city").toString()
                    )
                );
            }
            return cities;
        } else {
            return null;
        }
    }

    public List<CountryModel> getAllCountries() {
        Connection conn = this.getConnection();
        List<HashMap> countriesHash = this.selectAll(conn, "country");
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

    public List<AppointmentModel> getAllAppointments() {
        Connection conn = this.getConnection();
        List<HashMap> appointmentsHash = this.selectAll(conn, "appointment");
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
}