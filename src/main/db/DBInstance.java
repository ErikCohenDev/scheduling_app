package main.db;

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

    public Connection getConnection() {
        try {
            Class.forName(dbSecrets.getProperty("JDBC_DRIVER"));
            conn = DriverManager.getConnection(
                    dbSecrets.getProperty("DB_URL") + "?autoReconnect=true&useSSL=false",
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

    public List<HashMap> selectAll(Connection conn, String table) {
        ArrayList resultList = null;

        try {
            Statement stmt = conn.createStatement();
            String statement = "SELECT * FROM " + table + ";";
            System.out.println("Executing: " + statement);
            ResultSet rs = stmt.executeQuery(statement);
            if (rs.next()) {
                resultList = this.convertsResultToMap(rs);
            }
        } catch (SQLException var6) {
            var6.printStackTrace();
        }

        return resultList;
    }

    public List<HashMap> selectAndFilter(Connection conn, String table, String filterLabel, String filterValue) {
        List<HashMap> resultList = null;
        try {
            Statement stmt = conn.createStatement();
            String statement = "SELECT * FROM " + table + " WHERE " + filterLabel + "='" + filterValue + "';";
            System.out.println("Executing: " + statement);
            ResultSet rs = stmt.executeQuery(statement);
            if (rs.next() != false) {
                resultList = convertsResultToMap(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultList;
    }

    public List<HashMap> selectAndFilterByAuthUser(Connection conn, String table, int userId) {
        List<HashMap> resultList = null;
        try {
            Statement stmt = conn.createStatement();
            String statement = "SELECT * FROM " + table + " WHERE userId =" + userId + ";";
            System.out.println("Executing: " + statement);
            ResultSet rs = stmt.executeQuery(statement);
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
        rs.last();
        int rowCount = rs.getRow();
        rs.first();
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
}