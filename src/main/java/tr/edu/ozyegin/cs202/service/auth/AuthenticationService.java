package tr.edu.ozyegin.cs202.service.auth;

import tr.edu.ozyegin.cs202.model.User;
import tr.edu.ozyegin.cs202.model.UserType;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;
import tr.edu.ozyegin.cs202.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

public class AuthenticationService {

    public User login(String userId, String password) throws Exception {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            Validator.validateUserId(userId);
            Validator.validatePassword(password);

            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT users.id, users.first_name, users.last_name, users.user_type"
                            + " FROM users"
                            + " WHERE users.id=? AND users.password=SHA1(?);"
            );
            statement.setString(1, userId);
            statement.setString(2, password);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Utils.extractUser(resultSet, "users");
            } else {
                throw new Exception("Invalid user id or password");
            }
        } catch (Exception e) {
            Utils.logError(e);
            throw e;
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    public boolean register(String userId, String firstName, String lastName, String password) throws Exception {
        PreparedStatement statement = null;

        try {
            Validator.validateUserId(userId);
            Validator.validateFirstName(firstName);
            Validator.validateLastName(lastName);
            Validator.validatePassword(password);

            statement = DatabaseManager.getConnection().prepareStatement(
                    "INSERT INTO users (id, first_name, last_name, password, user_type)" +
                            " VALUES (?, ?, ?, SHA1(?), ?);"
            );

            statement.setString(1, userId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, password);
            statement.setInt(5, UserType.PATIENT.getId());

            int count = statement.executeUpdate();

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
