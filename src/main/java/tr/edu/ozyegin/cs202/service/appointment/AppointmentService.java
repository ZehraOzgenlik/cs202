package tr.edu.ozyegin.cs202.service.appointment;

import tr.edu.ozyegin.cs202.model.*;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class AppointmentService {

    public List<Appointment> getAppointments(Date startTime, Date endTime) throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Calendar calendar = Calendar.getInstance();
        if (startTime == null) {
            calendar.add(Calendar.YEAR, -100);
        } else {
            calendar.setTime(endTime);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        startTime = calendar.getTime();

        calendar = Calendar.getInstance();
        if (endTime == null) {
            calendar.add(Calendar.YEAR, 100);
        } else {
            calendar.setTime(endTime);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        endTime = calendar.getTime();

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT appointments.id, appointments.start_time, appointments.end_time, appointments.treatment_type, " +
                            " patient.id, patient.first_name, patient.last_name, patient.user_type, " +
                            " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type, " +
                            " departments.id, departments.name," +
                            " rooms.id, rooms.name"
                            + " FROM appointments"
                            + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                            + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                            + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                            + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                            + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                            + " WHERE appointments.start_time >= ? AND appointments.end_time <= ?"
                            + " ORDER BY appointments.id;"
            );
            statement.setTimestamp(1, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));

            resultSet = statement.executeQuery();

            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("appointments.id"));

                appointment.setId(resultSet.getInt("appointments.id"));
                appointment.setTreatmentType(TreatmentType.getById(resultSet.getInt("appointments.treatment_type")));
                appointment.setStartDate(new Date(resultSet.getTimestamp("appointments.start_time").getTime()));
                appointment.setEndDate(new Date(resultSet.getTimestamp("appointments.end_time").getTime()));

                Patient patient = (Patient) Utils.extractUser(resultSet, "patient");
                appointment.setPatient(patient);

                Doctor doctor = (Doctor) Utils.extractUser(resultSet, "doctor");
                Department department = new Department();
                department.setId(resultSet.getInt("departments.id"));
                department.setName(resultSet.getString("departments.name"));
                doctor.setDepartment(department);
                appointment.setDoctor(doctor);

                Room room = new Room();
                room.setId(resultSet.getInt("rooms.id"));
                room.setName(resultSet.getString("rooms.name"));
                appointment.setRoom(room);

                appointments.add(appointment);
            }
            return appointments;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    public Map<Department, Integer> getAppointmentsCountsByDepartment() throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT departments.id, departments.name, COUNT(*) AS count"
                            + " FROM appointments"
                            + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                            + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                            + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                            + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                            + " GROUP BY departments.id"
                            + " ORDER BY count"
            );

            resultSet = statement.executeQuery();

            Map<Department, Integer> appointmentCounts = new HashMap();
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getInt("departments.id"));
                department.setName(resultSet.getString("departments.name"));
                int count = resultSet.getInt("count");

                appointmentCounts.put(department, count);
            }
            return appointmentCounts;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }


    //These 3 method (getAppointmentList,deleteSelectedAppointment,getFilteredAppointments) include duplicated code since i dont want to disrupt the original coded. These codes may be merged.

    //method for getting appointments by current user and timeCode(indicates past or future appointments. [if timeCode == 1 past , timeCode == 2 future.])
    public List<Appointment> getAppointmentList(User user, int timeCode) throws ParseException, SQLException, ClassNotFoundException, IOException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = f.parse(currentTime);
        long currentMilliSecond = d.getTime();


        if (timeCode == 1) {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT appointments.id, appointments.start_time, appointments.end_time, appointments.treatment_type, " +
                            " patient.id, patient.first_name, patient.last_name, patient.user_type, " +
                            " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type, " +
                            " departments.id, departments.name," +
                            " rooms.id, rooms.name"
                            + " FROM appointments"
                            + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                            + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                            + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                            + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                            + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                            + " WHERE appointments.start_time <= ? AND patient.id = " + user.getId()
                            + " ORDER BY appointments.id;"
            );
            statement.setTimestamp(1, new java.sql.Timestamp(currentMilliSecond));
        } else if (timeCode == 2) {
            System.out.println("time code = 2 ------------------------------------------------------");
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT appointments.id, appointments.start_time, appointments.end_time, appointments.treatment_type, " +
                            " patient.id, patient.first_name, patient.last_name, patient.user_type, " +
                            " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type, " +
                            " departments.id, departments.name," +
                            " rooms.id, rooms.name"
                            + " FROM appointments"
                            + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                            + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                            + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                            + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                            + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                            + " WHERE appointments.start_time >= ? AND patient.id = " + user.getId()
                            + " ORDER BY appointments.id;"
            );
            statement.setTimestamp(1, new java.sql.Timestamp(currentMilliSecond));

        }

        try {


            resultSet = statement.executeQuery();

            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("appointments.id"));

                appointment.setId(resultSet.getInt("appointments.id"));
                appointment.setTreatmentType(TreatmentType.getById(resultSet.getInt("appointments.treatment_type")));
                appointment.setStartDate(new Date(resultSet.getTimestamp("appointments.start_time").getTime()));
                appointment.setEndDate(new Date(resultSet.getTimestamp("appointments.end_time").getTime()));

                Patient patient = (Patient) Utils.extractUser(resultSet, "patient");
                appointment.setPatient(patient);

                Doctor doctor = (Doctor) Utils.extractUser(resultSet, "doctor");
                Department department = new Department();
                department.setId(resultSet.getInt("departments.id"));
                department.setName(resultSet.getString("departments.name"));
                doctor.setDepartment(department);
                appointment.setDoctor(doctor);

                Room room = new Room();
                room.setId(resultSet.getInt("rooms.id"));
                room.setName(resultSet.getString("rooms.name"));
                appointment.setRoom(room);

                appointments.add(appointment);
            }
            return appointments;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    //method for delete selected items from appointments page
    public void deleteSelectedAppointment(String[] selections) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        if (selections != null && selections.length != 0) {
            try {
                connection = DatabaseManager.getConnection();
                statement = connection.createStatement();


                for (int i = 0; i < selections.length; i++) {
                    String appointmentDeleteQuery = "DELETE FROM appointments " + "WHERE id = " + selections[i] + ";";
                    statement.executeUpdate(appointmentDeleteQuery);
                }


            } catch (Exception e) {
                Utils.logError(e);
            } finally {

                DatabaseManager.closeStatement(statement);
                DatabaseManager.closeConnection();
            }
        }
    }

    //method for getting appointments filtered according to startTime, endTime, selectedDepartment.
    //filtering according to department is working.
    //filtering according to startTime and endTime is not working.
    //todo fix the filtering according to times
    public List<Appointment> getFilteredAppointments(User user, Date startTime, Date endTime, String selectedDepartment, int timeCode) throws SQLException, ClassNotFoundException, ParseException, IOException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Calendar calendar = Calendar.getInstance();
        if (startTime == null) {
            calendar.add(Calendar.YEAR, -100);
        } else {
            calendar.setTime(endTime);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        startTime = calendar.getTime();

        calendar = Calendar.getInstance();
        if (endTime == null) {
            calendar.add(Calendar.YEAR, 100);
        } else {
            calendar.setTime(endTime);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        endTime = calendar.getTime();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = f.parse(currentTime);
        long currentMilliSecond = d.getTime();

        System.out.println(currentMilliSecond);
        System.out.println("AppointmentService getFilteredAppointments ------------------------------" + startTime.getTime());

        if (!selectedDepartment.equals("all_departments")) {
            if (timeCode == 1) {
                statement = DatabaseManager.getConnection().prepareStatement(
                        "SELECT appointments.id, appointments.start_time, appointments.end_time, appointments.treatment_type, " +
                                " patient.id, patient.first_name, patient.last_name, patient.user_type, " +
                                " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type, " +
                                " departments.id, departments.name," +
                                " rooms.id, rooms.name"
                                + " FROM appointments"
                                + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                                + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                                + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                                + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                                + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                                + " WHERE appointments.start_time <= ? AND appointments.start_time >= ? AND appointments.end_time <= ? AND patient.id = " + user.getId() + " AND departments.name = '" + selectedDepartment + "' "
                                + " ORDER BY appointments.id;"
                );
                statement.setTimestamp(1, new java.sql.Timestamp(currentMilliSecond));
                statement.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
                statement.setTimestamp(3, new java.sql.Timestamp(endTime.getTime()));

            } else if (timeCode == 2) {
                statement = DatabaseManager.getConnection().prepareStatement(
                        "SELECT appointments.id, appointments.start_time, appointments.end_time, appointments.treatment_type, " +
                                " patient.id, patient.first_name, patient.last_name, patient.user_type, " +
                                " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type, " +
                                " departments.id, departments.name," +
                                " rooms.id, rooms.name"
                                + " FROM appointments"
                                + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                                + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                                + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                                + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                                + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                                + " WHERE  appointments.start_time >= ? AND appointments.start_time >= ? AND appointments.end_time <= ? AND patient.id = " + user.getId() + " AND departments.name = '" + selectedDepartment + "' "
                                + " ORDER BY appointments.id;"
                );
                statement.setTimestamp(1, new java.sql.Timestamp(currentMilliSecond));
                statement.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
                statement.setTimestamp(3, new java.sql.Timestamp(endTime.getTime()));
            }

        } else {
            if (timeCode == 1) {
                statement = DatabaseManager.getConnection().prepareStatement(
                        "SELECT appointments.id, appointments.start_time, appointments.end_time, appointments.treatment_type, " +
                                " patient.id, patient.first_name, patient.last_name, patient.user_type, " +
                                " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type, " +
                                " departments.id, departments.name," +
                                " rooms.id, rooms.name"
                                + " FROM appointments"
                                + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                                + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                                + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                                + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                                + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                                + " WHERE appointments.start_time <= ? AND appointments.start_time >= ? AND appointments.end_time <= ? AND patient.id = " + user.getId()
                                + " ORDER BY appointments.id;"
                );
                statement.setTimestamp(1, new java.sql.Timestamp(currentMilliSecond));
                statement.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
                statement.setTimestamp(3, new java.sql.Timestamp(endTime.getTime()));

            } else if (timeCode == 2) {
                statement = DatabaseManager.getConnection().prepareStatement(
                        "SELECT appointments.id, appointments.start_time, appointments.end_time, appointments.treatment_type, " +
                                " patient.id, patient.first_name, patient.last_name, patient.user_type, " +
                                " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type, " +
                                " departments.id, departments.name," +
                                " rooms.id, rooms.name"
                                + " FROM appointments"
                                + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                                + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                                + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                                + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                                + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                                + " WHERE  appointments.start_time >= ? AND appointments.start_time >= ? AND appointments.end_time <= ? AND patient.id = " + user.getId()
                                + " ORDER BY appointments.id;"
                );
                statement.setTimestamp(1, new java.sql.Timestamp(currentMilliSecond));
                statement.setTimestamp(2, new java.sql.Timestamp(startTime.getTime()));
                statement.setTimestamp(3, new java.sql.Timestamp(endTime.getTime()));
            }

        }

        try {
            resultSet = statement.executeQuery();

            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("appointments.id"));

                appointment.setId(resultSet.getInt("appointments.id"));
                appointment.setTreatmentType(TreatmentType.getById(resultSet.getInt("appointments.treatment_type")));
                appointment.setStartDate(new Date(resultSet.getTimestamp("appointments.start_time").getTime()));
                appointment.setEndDate(new Date(resultSet.getTimestamp("appointments.end_time").getTime()));

                Patient patient = (Patient) Utils.extractUser(resultSet, "patient");
                appointment.setPatient(patient);

                Doctor doctor = (Doctor) Utils.extractUser(resultSet, "doctor");
                Department department = new Department();
                department.setId(resultSet.getInt("departments.id"));
                department.setName(resultSet.getString("departments.name"));
                doctor.setDepartment(department);
                appointment.setDoctor(doctor);

                Room room = new Room();
                room.setId(resultSet.getInt("rooms.id"));
                room.setName(resultSet.getString("rooms.name"));
                appointment.setRoom(room);

                appointments.add(appointment);
            }
            return appointments;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }

    }

}


