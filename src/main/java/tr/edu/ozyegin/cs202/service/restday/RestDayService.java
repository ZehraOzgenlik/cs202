package tr.edu.ozyegin.cs202.service.restday;

import tr.edu.ozyegin.cs202.model.RestDay;
import tr.edu.ozyegin.cs202.model.User;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RestDayService {

    public List<RestDay> getRestDays(User user) throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT rest_days.id, rest_days.start_time, rest_days.end_time"
                            + " FROM rest_days"
                            + " INNER JOIN users AS doctor ON doctor.id = rest_days.user_id"
                            + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                            + " INNER JOIN departments ON doctor_departments.department_id = departments.id"
                            + " WHERE doctor.id=?"
                            + " ORDER BY rest_days.id"
            );

            statement.setString(1, user.getId());
            resultSet = statement.executeQuery();

            List<RestDay> restDays = new ArrayList<>();
            while (resultSet.next()) {
                RestDay restDay = new RestDay();

                restDay.setId(resultSet.getInt("rest_days.id"));
                restDay.setStartDate(new Date(resultSet.getTimestamp("rest_days.start_time").getTime()));
                restDay.setEndDate(new Date(resultSet.getTimestamp("rest_days.end_time").getTime()));

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

    public boolean saveRestDay(User user, Date startTime, Date endTime) throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        if (Utils.isDayPassed(startTime)) {
            throw new IOException("This record could not be saved.");
        }

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "INSERT INTO rest_days(user_id, start_time, end_time)"
                            + " VALUES (?, ?, ?)"
            );

            statement.setString(1, user.getId());
            statement.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(3, new java.sql.Timestamp(endTime.getTime()));

            int count = statement.executeUpdate();

            return count == 1;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    public boolean deleteRestDay(User user, Date startTime, Date endTime) throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        if (Utils.isDayPassed(startTime)) {
            throw new IOException("This record could not be deleted.");
        }

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "DELETE FROM rest_days"
                            + " WHERE user_id=? AND end_time=? AND start_time=?"
            );

            statement.setString(1, user.getId());
            statement.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(3, new java.sql.Timestamp(endTime.getTime()));

            int count = statement.executeUpdate();

            return count == 1;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }
}
