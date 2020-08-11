package main.db;

import main.model.UserModel;

import java.sql.Connection;
import java.util.ArrayList;
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

    public static List<UserModel> getAll() {
        Connection conn = DBInstance.getInstance().getConnection();
        List<UserModel> userList = new ArrayList<>();
        List<HashMap> userData = DBInstance.getInstance().selectAll(conn, "user");
        if (userData != null && userData.size() > 0) {
            for (HashMap user: userData) {
                userList.add(new UserModel(
                        Integer.parseInt(String.valueOf(user.get("userId"))),
                        String.valueOf(user.get("userName")),
                        Integer.parseInt(String.valueOf(user.get("active"))) == 1
                    )
                );
            }
            return userList;
        }
        return null;
    }
}
