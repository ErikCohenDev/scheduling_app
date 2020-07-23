package main.db;

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
            return user;
        }
        return null;
    }

    private static HashMap<String, String> getUserInfo(String userName) {
        return DBInstance.getInstance().getUserByUserName(userName);
    }

    public static void logout() {
        user = null;
    }
}
