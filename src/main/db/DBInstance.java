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
}