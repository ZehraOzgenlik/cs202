package tr.edu.ozyegin.cs202.controller.home.patient.newappointment;

import tr.edu.ozyegin.cs202.model.*;
import tr.edu.ozyegin.cs202.service.department.DepartmentService;
import tr.edu.ozyegin.cs202.service.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@WebServlet(name = "PatientServlet", urlPatterns = {"/patient/new_appointment", "/patient/home_patient.jsp"})
public class NewAppointmentServlet extends HttpServlet {

    private UserService userService = new UserService();
    private DepartmentService departmentService = new DepartmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showAvailableDoctors(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addNewAppointments(req, resp);
        showAvailableDoctors(req, resp);
    }

    private void showAvailableDoctors(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<Department> departments = departmentService.getDepartments();
        Date startTime = toDate(request.getParameter("startTime"));
        Date endTime = toDate(request.getParameter("endTime"));
        String departmentId = request.getParameter("department");

        List<Doctor> doctors = userService.getAvailableDoctors(startTime, endTime, departmentId);

        request.setAttribute("startTime", toString(startTime));
        request.setAttribute("endTime", toString(endTime));
        request.setAttribute("selectedDepartment", departmentId);
        request.setAttribute("departments", departments);
        request.setAttribute("doctors", doctors);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("new_appointment.jsp");
        requestDispatcher.include(request, response);
    }

    private void addNewAppointments(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        User currentPatient = (User) session.getAttribute("currentUser");

        String selectedDoctorID = request.getParameter("doctorID");

        Date appointmentStartTime = toDate(request.getParameter("appointmentStartTime"));
        Date appointmentEndTime = toDate(request.getParameter("appointmentEndTime"));

        TreatmentType treatmentType = TreatmentType.OUTPATIENT;

        userService.addNewAppointments(currentPatient, selectedDoctorID, appointmentStartTime, appointmentEndTime, treatmentType);

    }

    private String toString(Date date) {
        String datePattern = "yyyy-MM-dd'T'HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
        if (date != null) {
            try {
                return simpleDateFormat.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Date toDate(String dateWord) {
        String pattern = "yyyy-MM-dd'T'HH:mm";
        return toDate(dateWord, pattern);
    }

    private Date toDate(String dateWord, String pattern) {
        if (dateWord != null && dateWord.length() > 0) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                return dateFormat.parse(dateWord);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
