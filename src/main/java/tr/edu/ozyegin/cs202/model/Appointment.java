package tr.edu.ozyegin.cs202.model;

import java.util.Calendar;
import java.util.Date;

public class Appointment {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private Room room;
    private TreatmentType treatmentType;
    private Date startDate;
    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public void setTreatmentType(TreatmentType treatmentType) {
        this.treatmentType = treatmentType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getStartYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        return calendar.get(Calendar.YEAR);
    }

    public int getStartMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        return calendar.get(Calendar.MONTH);
    }

    public int getStartDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getStartHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getStartMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        return calendar.get(Calendar.MINUTE);
    }

    public int getEndYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        return calendar.get(Calendar.YEAR);
    }

    public int getEndMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        return calendar.get(Calendar.MONTH);
    }

    public int getEndDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getEndHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public int getEndMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        return calendar.get(Calendar.MINUTE);
    }
}
