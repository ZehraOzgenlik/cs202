package tr.edu.ozyegin.cs202.service.auth;

import tr.edu.ozyegin.cs202.model.User;
import tr.edu.ozyegin.cs202.model.UserType;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;
import tr.edu.ozyegin.cs202.util.Validator;

import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class AuthenticationService {

    public User login(String userId, String password) throws Exception {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Validator.validateUserId(userId);
            Validator.validatePassword(password);

            statement = DatabaseManager.getConnection().createStatement();
            resultSet = statement.executeQuery(
                    "SELECT user.id, user.first_name, user.last_name, user.user_type"
                            + " FROM Users AS user"
                            + " WHERE user.id='" + userId + "' AND user.password=SHA1('" + password + "');"
            );

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("user.id"));
                user.setFirstName(resultSet.getString("user.first_name"));
                user.setLastName(resultSet.getString("user.last_name"));
                user.setUserType(UserType.getById(resultSet.getInt("user.user_type")));
                return user;
            }
            return null;
        } catch (Exception e) {
            Utils.logError(e);
            throw e;
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    public boolean register(String userId, String firstName, String lastName, String password) throws Exception {
        Statement statement = null;

        try {
            Validator.validateUserId(userId);
            Validator.validateFirstName(firstName);
            Validator.validateLastName(lastName);
            Validator.validatePassword(password);

            statement = DatabaseManager.getConnection().createStatement();
            int count = statement.executeUpdate(
                    "INSERT INTO users (id, first_name, last_name, password, user_type)" +
                            " VALUES ('" + userId + "', '" + firstName + "', '" + lastName + "', SHA1('" + password + "'), " + UserType.PATIENT.getId() + ");"
            );

            return count == 1;
        } catch (SQLIntegrityConstraintViolationException e) {
            Utils.logError(e);
            throw new Exception("You have already registered an account.");
        } catch (Exception e) {
            Utils.logError(e);
            throw e;
        } finally {
            DatabaseManager.closeStatement(statement);
        }
    }
}
