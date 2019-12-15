package tr.edu.ozyegin.cs202.repository;

import tr.edu.ozyegin.cs202.util.Utils;

import java.sql.*;

public class DatabaseManager {

    private static final String DB_NAME = "cs202";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    private static final String DB_PARAMS = "?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "emir";
    private static final String PASS = "12345";

    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            return openConnection();
        }

        Statement statement = null;
        try {
            /* Start JDBC Connection test */
            statement = connection.createStatement();
            statement.executeQuery("SELECT 1");
            return connection;
        } catch (Exception ex) {
            return openConnection();
        } finally {
            closeStatement(statement);
        }
    }

    public static Connection openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL + DB_PARAMS, USER, PASS);
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
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
}
