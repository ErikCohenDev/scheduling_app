package main.model;

import main.util.DateUtils;

import java.io.FileWriter;
import java.io.IOException;

public class ActivityLog {
    public static void stamp(UserModel user) {
        try {
            String filename= "log.txt";
            FileWriter fw = new FileWriter(filename,true);
            fw.write(user.getUsername() + " : " + DateUtils.getLocalStringNow() + "\n");
            fw.close();
        } catch(IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
}
