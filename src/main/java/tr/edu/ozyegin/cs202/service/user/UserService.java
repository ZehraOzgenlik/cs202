package tr.edu.ozyegin.cs202.service.user;

import tr.edu.ozyegin.cs202.model.*;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public List<Doctor> getAvailableDoctors(Date startTime, String departmentID) throws IOException {

        Calendar calendar = Calendar.getInstance();
        if (startTime == null) {
            calendar.add(Calendar.YEAR, -100);
        } else if (calendar.getTime().after(startTime)) {
            calendar.setTime(new Date());
        } else {
            calendar.setTime(startTime);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startTime = calendar.getTime();

        calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endTime = calendar.getTime();

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT doctor.id, doctor.first_name, doctor.last_name, doctor.user_type,"
                            + " departments.id,departments.name"
                            + " FROM users as doctor"
                            + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                            + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                            + " WHERE doctor.id IN ("
                            + "     SELECT doctor_id FROM doctor_departments WHERE doctor_id NOT IN ("
                            + "         SELECT user_id FROM rest_days"
                            + "         WHERE (rest_days.start_time >= ? AND rest_days.start_time <= ?)"
                            + "         OR (rest_days.end_time >= ? AND rest_days.end_time <= ?)"
                            + "         UNION"
                            + "         SELECT doctor_id FROM appointments"
                            + "         WHERE (appointments.start_time >= ? AND appointments.start_time <= ?)"
                            + "         OR (appointments.end_time >= ? AND appointments.end_time <= ?)"
                            + "      )"
                            + "     AND department_id LIKE IFNULL(?, '%')"
                            + " )"
            );

            statement.setTimestamp(1, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));
            statement.setTimestamp(3, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(4, new java.sql.Timestamp(endTime.getTime()));
            statement.setTimestamp(5, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(6, new java.sql.Timestamp(endTime.getTime()));
            statement.setTimestamp(7, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(8, new java.sql.Timestamp(endTime.getTime()));
            statement.setString(9, departmentID);

            resultSet = statement.executeQuery();

            List<Doctor> doctors = new ArrayList<>();
            while (resultSet.next()) {

                Doctor doctor = (Doctor) Utils.extractUser(resultSet, "doctor");
                Department department = new Department();
                department.setId(resultSet.getInt("departments.id"));
                department.setName(resultSet.getString("departments.name"));
                doctor.setDepartment(department);

                doctors.add(doctor);
            }

            return doctors;

        } catch (
                Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }
}

