package tr.edu.ozyegin.cs202.controller.home.patient.appointments;

import tr.edu.ozyegin.cs202.model.*;
import tr.edu.ozyegin.cs202.service.appointment.AppointmentService;
import tr.edu.ozyegin.cs202.service.department.DepartmentService;
import tr.edu.ozyegin.cs202.service.user.UserService;
import tr.edu.ozyegin.cs202.util.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AppointmentServlet", urlPatterns = {"/patient/appointments", "/doctor/appointments"})
public class AppointmentServlet extends HttpServlet {

    private AppointmentService appointmentService = new AppointmentService();
    private UserService userService = new UserService();
    private DepartmentService departmentService = new DepartmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            showAppointment(request, response);
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("cancelAppointments".equals(request.getParameter("action"))) {
            String[] selections = request.getParameterValues("appointmentId");

            try {
                appointmentService.deleteSelectedAppointment(selections);
                showAppointment(request, response);
            } catch (Exception e) {
                showError(request, response, e.getMessage());
            }
        }
    }

    private void showAppointment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User currentUser = (User) session.getAttribute("currentUser");
        String patientId;
        String doctorId;
        List<Patient> patients;
        List<Department> departments;
        if (currentUser.getUserType() == UserType.PATIENT) {
            patientId = currentUser.getId();
            doctorId = null;
            patients = new ArrayList<>();
            departments = departmentService.getDepartments();
        } else {
            patientId = request.getParameter("patient");
            doctorId = currentUser.getId();
            patients = userService.getPatients();
            departments = new ArrayList<>();
        }

        String timeCode = request.getParameter("timeCode");
        if (timeCode == null) {
            timeCode = (String) session.getAttribute("timeCode");
        }

        Date startTime = Utils.toDate(request.getParameter("startTime"));
        Date endTime = Utils.toDate(request.getParameter("endTime"));
        String departmentId = request.getParameter("department");

        List<Appointment> appointments = appointmentService.getAppointments(patientId,
                doctorId,
                startTime,
                endTime,
                timeCode,
                departmentId
        );

        session.setAttribute("timeCode", timeCode);
        request.setAttribute("appointments", appointments);
        request.setAttribute("startTime", Utils.toString(startTime));
        request.setAttribute("endTime", Utils.toString(endTime));
        request.setAttribute("selectedDepartment", departmentId);
        request.setAttribute("departments", departments);
        request.setAttribute("selectedPatient", patientId);
        request.setAttribute("patients", patients);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("appointments.jsp");
        requestDispatcher.include(request, response);
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.getSession(false).invalidate();
        request.setAttribute("error", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("appointments.jsp");
        requestDispatcher.forward(request, response);
    }
}
