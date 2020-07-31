package main.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

public class DBUser {
    public static HashMap getUserByUserName(String userName) {
        Connection conn = DBInstance.getInstance().getConnection();
        List<HashMap> userData = DBInstance.getInstance().selectAndFilter(conn, "user", "userName", userName);
        if (userData != null && userData.size() > 0) {
            return userData.get(0);
        }
        return null;
    }
}
