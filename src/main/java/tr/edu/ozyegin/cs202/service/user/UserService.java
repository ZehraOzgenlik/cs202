package tr.edu.ozyegin.cs202.service.user;

import tr.edu.ozyegin.cs202.model.Department;
import tr.edu.ozyegin.cs202.model.Doctor;
import tr.edu.ozyegin.cs202.model.Patient;
import tr.edu.ozyegin.cs202.model.UserType;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public List<Patient> getPatients() throws IOException {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().createStatement();
            resultSet = statement.executeQuery(
                    "SELECT user.id, user.first_name, user.last_name"
                            + " FROM users AS user"
                            + " WHERE user.user_type=" + UserType.PATIENT.getId() + ";"
            );

            List<Patient> patients = new ArrayList<>();
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getString("user.id"));
                patient.setFirstName(resultSet.getString("user.first_name"));
                patient.setLastName(resultSet.getString("user.last_name"));
                patient.setUserType(UserType.PATIENT);
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
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().createStatement();
            resultSet = statement.executeQuery(
                    "SELECT user.id, user.first_name, user.last_name, department.id, department.name"
                            + " FROM users AS user"
                            + " LEFT OUTER JOIN doctor_departments ON doctor_departments.doctor_id = user.id"
                            + " LEFT OUTER JOIN departments as department ON department.id = doctor_departments.department_id"
                            + " WHERE user.user_type=" + UserType.DOCTOR.getId() + ";"
            );

            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {
                Doctor doctor = new Doctor();
                doctor.setId(resultSet.getString("user.id"));
                doctor.setFirstName(resultSet.getString("user.first_name"));
                doctor.setLastName(resultSet.getString("user.last_name"));
                doctor.setUserType(UserType.DOCTOR);
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
