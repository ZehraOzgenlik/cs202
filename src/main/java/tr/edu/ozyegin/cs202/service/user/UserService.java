package tr.edu.ozyegin.cs202.service.user;

import tr.edu.ozyegin.cs202.model.Department;
import tr.edu.ozyegin.cs202.model.Doctor;
import tr.edu.ozyegin.cs202.model.Patient;
import tr.edu.ozyegin.cs202.model.UserType;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<Patient> getPatients() throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT user.id, user.first_name, user.last_name, user.user_type"
                            + " FROM users AS user"
                            + " WHERE user.user_type=?;"
            );
            statement.setInt(1, UserType.PATIENT.getId());
            resultSet = statement.executeQuery();

            List<Patient> patients = new ArrayList<>();
            while (resultSet.next()) {
                Patient patient = (Patient) Utils.extractUser(resultSet, "user");
                patients.add(patient);
            }
            return patients;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    public List<Doctor> getDoctors() throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT user.id, user.first_name, user.last_name, user.user_type, department.id, department.name"
                            + " FROM users AS user"
                            + " LEFT OUTER JOIN doctor_departments ON doctor_departments.doctor_id = user.id"
                            + " LEFT OUTER JOIN departments as department ON department.id = doctor_departments.department_id"
                            + " WHERE user.user_type=?;"
            );
            statement.setInt(1, UserType.DOCTOR.getId());
            resultSet = statement.executeQuery();

            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {
                Doctor doctor = (Doctor) Utils.extractUser(resultSet, "user");
                Department department = new Department();
                department.setId(resultSet.getInt("department.id"));
                department.setName(resultSet.getString("department.name"));
                doctor.setDepartment(department);
                doctors.add(doctor);
            }
            return doctors;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }
}
