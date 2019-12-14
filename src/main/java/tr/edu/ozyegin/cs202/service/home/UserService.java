package tr.edu.ozyegin.cs202.service.home;

import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.service.model.Department;
import tr.edu.ozyegin.cs202.service.model.Doctor;
import tr.edu.ozyegin.cs202.service.model.UserType;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<Doctor> getDoctors() throws IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseManager.openConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    "SELECT user.id, user.first_name, user.last_name, userType.id, userType.name"
                            + " FROM users AS user"
                            + " INNER JOIN user_types AS userType ON userType.id = user.user_type"
                            + " WHERE user.user_type=" + 2 + ";"
            );

            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.id = resultSet.getString("user.id");
                doctor.firstName = resultSet.getString("user.first_name");
                doctor.lastName = resultSet.getString("user.last_name");
                doctor.userType = new UserType(
                        resultSet.getInt("userType.id"),
                        resultSet.getString("userType.name")
                );

                doctor.departments = getDoctorDepartments(doctor.id);
                doctors.add(doctor);
            }
            return doctors;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
            DatabaseManager.closeConnection(connection);
        }
    }

    public List<Department> getDoctorDepartments(String doctorId) throws IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseManager.openConnection();
            statement = connection.createStatement();

            resultSet = statement.executeQuery(
                    "SELECT departments.id, departments.name"
                            + " FROM doctor_departments"
                            + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                            + " WHERE doctor_departments.user_id='" + doctorId + "';"
            );

            List<Department> departments = new ArrayList<>();
            while (resultSet.next()) {
                departments.add(new Department(
                        resultSet.getInt("departments.id"),
                        resultSet.getString("departments.name")
                ));
            }
            return departments;
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
