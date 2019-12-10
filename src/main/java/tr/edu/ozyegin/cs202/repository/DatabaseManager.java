package tr.edu.ozyegin.cs202.repository;

import tr.edu.ozyegin.cs202.util.Utils;

import java.sql.*;
import java.util.Scanner;

public class DatabaseManager {

    private static final String DB_NAME = "cs202";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String USER = "emir";
    private static final String PASS = "12345";

    public static Connection openConnection() throws SQLException, ClassNotFoundException {
        return openConnection(DB_URL);
    }

    public static Connection openConnection(String url) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DB_URL + "?useUnicode=true&characterEncoding=UTF-8&&useLegacyDatetimeCode=false&serverTimezone=Turkey", USER, PASS);
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            Utils.logError(e);
        }
    }

    public static void closeStatement(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            Utils.logError(e);
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {
            Utils.logError(e);
        }
    }

    public static String readSelection() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            return scanner.next();
        }
        return "";
    }
}
