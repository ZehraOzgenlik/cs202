package tr.edu.ozyegin.cs202.service.login;

import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.service.model.User;
import tr.edu.ozyegin.cs202.service.model.UserType;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginService {

    public User login(String userId, String password) throws IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseManager.openConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    "SELECT user.id, user.first_name, user.last_name, userType.id, userType.name"
                            + " FROM Users AS user"
                            + " INNER JOIN user_types AS userType ON userType.id = user.user_type"
                            + " WHERE user.id='" + userId + "' AND user.password=SHA1('" + password + "');"
            );

            if (resultSet.next()) {
                User user = new User();
                user.id = resultSet.getString("user.id");
                user.firstName = resultSet.getString("user.first_name");
                user.lastName = resultSet.getString("user.last_name");
                user.userType = new UserType(
                        resultSet.getInt("userType.id"),
                        resultSet.getString("userType.name")
                );
                return user;
            }
            return null;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
            DatabaseManager.closeConnection(connection);
        }
    }
}
