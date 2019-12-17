package tr.edu.ozyegin.cs202.service.resday;


import tr.edu.ozyegin.cs202.model.Department;
import tr.edu.ozyegin.cs202.model.Doctor;
import tr.edu.ozyegin.cs202.model.RestDay;
import tr.edu.ozyegin.cs202.model.User;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RestDayService {

    public boolean isDayPassed(RestDay restDay) {
        //todo -- This method will check if the rest day passed or not. If rest day is not passed then it can be canceled.
        // That is,there will be checkbox at the beginning of the corresponding row.
        return false;
    }

    public void deleteSelectedRestDays(String[] daySelections) {
        //todo -- This method will delete the selected rest days from DB.
        // parameter daySelection holds the RestDay IDs.

    }

    public List<RestDay> getRestDays(User user) throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT rest_days.id,rest_days.start_time,rest_days.end_time,doctor.id,doctor.first_name,doctor.last_name,doctor.user_type,departments.id,departments.name" +
                            " FROM rest_days" +
                            " INNER JOIN users AS doctor ON doctor.id = rest_days.user_id" +
                            " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id" +
                            " INNER JOIN departments ON doctor_departments.department_id = departments.id" +
                            " WHERE doctor.id = " + user.getId() +
                            " ORDER BY rest_days.id;"
            );

            resultSet = statement.executeQuery();

            List<RestDay> restDays = new ArrayList<>();
            while (resultSet.next()) {
                RestDay restDay = new RestDay();


                restDay.setId(resultSet.getInt("rest_days.id"));
                restDay.setStartDate(new Date(resultSet.getTimestamp("rest_days.start_time").getTime()));
                restDay.setEndDate(new Date(resultSet.getTimestamp("rest_days.end_time").getTime()));

                Doctor doctor = (Doctor) Utils.extractUser(resultSet, "doctor");
                Department department = new Department();
                department.setId(resultSet.getInt("departments.id"));
                department.setName(resultSet.getString("departments.name"));
                doctor.setDepartment(department);
                restDay.setDoctor(doctor);

                restDays.add(restDay);
            }
            return restDays;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }
}
