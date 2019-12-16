package tr.edu.ozyegin.cs202.service.appointment;

import tr.edu.ozyegin.cs202.model.*;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
}