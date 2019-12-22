package tr.edu.ozyegin.cs202.service.room;

import tr.edu.ozyegin.cs202.model.*;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoomService {

    public List<Room> getRooms() throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT rooms.id, rooms.name"
                            + " FROM rooms"
            );
            resultSet = statement.executeQuery();

            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                Room room = new Room();
                room.setId(resultSet.getInt("rooms.id"));
                room.setName(resultSet.getString("rooms.name"));

                rooms.add(room);
            }
            return rooms;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    public List<Appointment> getReservedTimes(String roomName) throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT appointments.id, appointments.start_time, appointments.end_time,"
                            + " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type,"
                            + " patient.id, patient.first_name, patient.last_name, patient.user_type,"
                            + " departments.id, departments.name,"
                            + " rooms.id, rooms.name"
                            + " FROM rooms"
                            + " INNER JOIN appointments ON appointments.room_id = rooms.id"
                            + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                            + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                            + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                            + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                            + " WHERE rooms.name LIKE IFNULL(?, '%')"
            );
            statement.setString(1, roomName);
            resultSet = statement.executeQuery();

            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("appointments.id"));

                Doctor doctor = (Doctor) Utils.extractUser(resultSet, "doctor");
                Department department = new Department();
                department.setId(resultSet.getInt("departments.id"));
                department.setName(resultSet.getString("departments.name"));
                doctor.setDepartment(department);
                appointment.setDoctor(doctor);

                Patient patient = (Patient) Utils.extractUser(resultSet, "patient");
                appointment.setPatient(patient);

                appointment.setStartDate(new Date(resultSet.getTimestamp("appointments.start_time").getTime()));
                appointment.setEndDate(new Date(resultSet.getTimestamp("appointments.end_time").getTime()));

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

    //This method gets 1 available room for given time interval.
    public Room getAvailableRoom(Date startTime, Date endTime) throws IOException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT rooms.id, rooms.name"
                            + " FROM rooms WHERE id NOT IN ("
                            + "     SELECT appointments.room_id"
                            + "     FROM appointments"
                            + "     WHERE (appointments.start_time >= ? AND appointments.start_time <= ?)"
                            + "     OR (appointments.end_time >= ? AND appointments.end_time <= ?)"
                            + ") LIMIT 1"
            );

            statement.setTimestamp(1, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));
            statement.setTimestamp(3, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(4, new java.sql.Timestamp(endTime.getTime()));

            resultSet = statement.executeQuery();

            Room room = new Room();

            if (resultSet.next()) {
                room.setId(resultSet.getInt("rooms.id"));
                room.setName(resultSet.getString("rooms.name"));
            }

            return room;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }
}
