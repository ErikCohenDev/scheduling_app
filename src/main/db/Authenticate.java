package main.db;

import main.model.ActivityLog;
import main.model.UserModel;
import java.util.HashMap;

public abstract class Authenticate {
    public static UserModel user;

    public static UserModel login(String userName, String password) {
        HashMap<String, String> userInfo = getUserInfo(userName);
        if (userInfo != null && password.equals(userInfo.get("password"))) {
            Integer userId = Integer.valueOf(String.valueOf(userInfo.get("userId")));
            boolean active = Integer.parseInt(String.valueOf(userInfo.get("active"))) ==  1 ? true : false;
            user = new UserModel(userId, userName, active);
            ActivityLog.stamp(user);
            return user;
        }
        return null;
    }

    private static HashMap getUserInfo(String userName) {
        return DBUser.getUserByUserName(userName);
    }

    public static void logout() {
        user = null;
    }
}
