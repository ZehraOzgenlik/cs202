package tr.edu.ozyegin.cs202.service.appointment;

import tr.edu.ozyegin.cs202.model.*;
import tr.edu.ozyegin.cs202.repository.DatabaseManager;
import tr.edu.ozyegin.cs202.service.room.RoomService;
import tr.edu.ozyegin.cs202.util.Utils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class AppointmentService {

    public List<Appointment> getAppointments(
            String patientId,
            String doctorId,
            Date startTime,
            Date endTime,
            String timeCode,
            String departmentId) throws IOException {

        Calendar calendar = Calendar.getInstance();
        if (timeCode == null && startTime == null) {
            calendar.add(Calendar.YEAR, -100);
        } else if (timeCode == null) {
            calendar.setTime(startTime);
        } else {
            if ("future".equals(timeCode)) {
                if (startTime == null || calendar.getTime().after(startTime)) {
                    calendar.setTime(new Date());
                } else {
                    calendar.setTime(startTime);
                }
            } else if ("past".equals(timeCode)) {
                if (startTime == null || calendar.getTime().before(startTime)) {
                    calendar.add(Calendar.YEAR, -100);
                } else {
                    calendar.setTime(startTime);
                }
            }
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startTime = calendar.getTime();

        calendar = Calendar.getInstance();
        if (timeCode == null && endTime == null) {
            calendar.add(Calendar.YEAR, 100);
        } else if (timeCode == null) {
            calendar.setTime(endTime);
        } else {
            if ("future".equals(timeCode)) {
                if (endTime == null || calendar.getTime().after(endTime)) {
                    calendar.add(Calendar.YEAR, 100);
                } else {
                    calendar.setTime(endTime);
                }
            } else if ("past".equals(timeCode)) {
                if (endTime == null || calendar.getTime().before(endTime)) {
                    calendar.setTime(new Date());
                } else {
                    calendar.setTime(endTime);
                }
            }
        }
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endTime = calendar.getTime();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT appointments.id, appointments.start_time, appointments.end_time, appointments.treatment_type,"
                            + " patient.id, patient.first_name, patient.last_name, patient.user_type,"
                            + " doctor.id, doctor.first_name, doctor.last_name, doctor.user_type,"
                            + " departments.id, departments.name,"
                            + " rooms.id, rooms.name"
                            + " FROM appointments"
                            + " INNER JOIN users AS patient ON patient.id = appointments.patient_id"
                            + " INNER JOIN rooms ON appointments.room_id = rooms.id"
                            + " INNER JOIN users AS doctor ON doctor.id = appointments.doctor_id"
                            + " INNER JOIN doctor_departments ON doctor_departments.doctor_id = doctor.id"
                            + " INNER JOIN departments ON departments.id = doctor_departments.department_id"
                            + " WHERE appointments.start_time >= ? AND appointments.end_time <= ?"
                            + " AND doctor.id LIKE IFNULL(?, '%')"
                            + " AND patient.id LIKE IFNULL(?, '%')"
                            + " AND departments.id LIKE IFNULL(?, '%')"
                            + " ORDER BY appointments.id;"
            );
            statement.setTimestamp(1, new java.sql.Timestamp(startTime.getTime()));
            statement.setTimestamp(2, new java.sql.Timestamp(endTime.getTime()));
            statement.setString(3, doctorId);
            statement.setString(4, patientId);
            statement.setString(5, departmentId);

            resultSet = statement.executeQuery();

            List<Appointment> appointments = new ArrayList<>();
            while (resultSet.next()) {
                Appointment appointment = new Appointment();
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
                            + " ORDER BY count DESC"
            );

            resultSet = statement.executeQuery();

            Map<Department, Integer> appointmentCounts = new LinkedHashMap<>();
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

    public Appointment getAppointment(int appointmentId) throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = DatabaseManager.getConnection().prepareStatement(
                    "SELECT *"
                            + " FROM appointments"
                            + " WHERE id=?"
            );

            statement.setInt(1, appointmentId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(resultSet.getInt("appointments.id"));

                appointment.setTreatmentType(TreatmentType.getById(resultSet.getInt("appointments.treatment_type")));
                appointment.setStartDate(new Date(resultSet.getTimestamp("appointments.start_time").getTime()));
                appointment.setEndDate(new Date(resultSet.getTimestamp("appointments.end_time").getTime()));

                return appointment;
            }
            return null;
        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    public void addNewAppointments(User currentPatient, String selectedDoctorID, Date appointmentStartTime, TreatmentType treatmentType) throws IOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        if (selectedDoctorID == null || selectedDoctorID.length() == 0) {
            throw new IOException("You should select a doctor");
        }

        if (appointmentStartTime == null) {
            throw new IOException("You should select appointment time");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(appointmentStartTime);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        Date appointmentEndTime = calendar.getTime();

        try {

            statement = DatabaseManager.getConnection().prepareStatement(
                    "INSERT INTO appointments (patient_id, doctor_id, room_id, treatment_type, start_time, end_time) "
                            + "VALUES (?, ?, ?, ?, ?, ?)"
            );

            statement.setString(1, currentPatient.getId());
            statement.setString(2, selectedDoctorID);
            statement.setInt(3, new RoomService().getAvailableRoom(appointmentStartTime, appointmentEndTime).getId());
            statement.setInt(4, treatmentType.getId());
            statement.setTimestamp(5, new java.sql.Timestamp(appointmentStartTime.getTime()));
            statement.setTimestamp(6, new java.sql.Timestamp(appointmentEndTime.getTime()));

            statement.executeUpdate();

        } catch (Exception e) {
            Utils.logError(e);
            throw new IOException(e);
        } finally {
            DatabaseManager.closeResultSet(resultSet);
            DatabaseManager.closeStatement(statement);
        }
    }

    public boolean deleteSelectedAppointment(String appointmentId) throws IOException {
        if (appointmentId != null && appointmentId.length() > 0) {

            PreparedStatement statement = null;

            Appointment appointment = getAppointment(Integer.parseInt(appointmentId));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date nextDay = calendar.getTime();
            if (nextDay.after(appointment.getStartDate())) {
                throw new IOException("You cannot this appointment because there are less than 24 hours left");
            }

            try {
                statement = DatabaseManager.getConnection().prepareStatement(
                        "DELETE FROM appointments WHERE appointments.id=?"
                );

                statement.setInt(1, Integer.parseInt(appointmentId));
                int count = statement.executeUpdate();
                return count == 1;
            } catch (Exception e) {
                Utils.logError(e);
                throw new IOException(e);
            } finally {
                DatabaseManager.closeStatement(statement);
            }
        }
        return false;
    }
}
