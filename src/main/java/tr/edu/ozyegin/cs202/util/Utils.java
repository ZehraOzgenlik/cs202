package tr.edu.ozyegin.cs202.util;

import tr.edu.ozyegin.cs202.model.*;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static User extractUser(ResultSet resultSet, String prefix) throws Exception {
        String id = resultSet.getString(prefix + ".id");
        String firstName = resultSet.getString(prefix + ".first_name");
        String lastName = resultSet.getString(prefix + ".last_name");
        UserType userType = UserType.getById(resultSet.getInt(prefix + ".user_type"));

        User user = null;
        switch (userType) {
            case PATIENT:
                user = new Patient();
                break;
            case DOCTOR:
                user = new Doctor();
                break;
            case NURSE:
                user = new Nurse();
                break;
            case MANAGER:
                user = new Manager();
                break;
        }

        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserType(userType);
        return user;
    }

    public static void logError(Exception error) {
        error.printStackTrace();
    }

    public static Date toDate(String date) {
        if (date != null) {
            try {
                return dateFormat.parse(date);
            } catch (Exception e) {
                logError(e);
            }
        }
        return null;
    }

    public static String toString(Date date) {
        if (date != null) {
            try {
                return dateFormat.format(date);
            } catch (Exception e) {
                logError(e);
            }
        }
        return null;
    }
}
