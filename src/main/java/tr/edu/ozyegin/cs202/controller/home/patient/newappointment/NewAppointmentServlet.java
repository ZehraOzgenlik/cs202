package tr.edu.ozyegin.cs202.controller.home.patient.newappointment;

import tr.edu.ozyegin.cs202.model.Department;
import tr.edu.ozyegin.cs202.model.Doctor;
import tr.edu.ozyegin.cs202.model.TreatmentType;
import tr.edu.ozyegin.cs202.model.User;
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
import java.util.Date;
import java.util.List;


@WebServlet(name = "PatientServlet", urlPatterns = {"/patient/new_appointment", "/patient/home_patient.jsp"})
public class NewAppointmentServlet extends HttpServlet {

    private UserService userService = new UserService();
    private AppointmentService appointmentService = new AppointmentService();
    private DepartmentService departmentService = new DepartmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments = departmentService.getDepartments();
        request.setAttribute("departments", departments);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("new_appointment.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("filter".equals(Utils.getParameter(request, "action"))) {
            showAvailableDoctors(request, response);
        } else if ("save".equals(Utils.getParameter(request, "action"))) {
            addNewAppointments(request, response);
            showMessage(request, response, "Appointment saved successfully");
        }
    }

    private void showAvailableDoctors(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Department> departments = departmentService.getDepartments();
        String startTime = Utils.getParameter(request, "startTime");
        String endTime = Utils.getParameter(request, "endTime");
        String departmentId = Utils.getParameter(request, "department");

        Date startDate = Utils.toDate(startTime, "yyyy-MM-dd'T'HH:mm");
        Date endDate = Utils.toDate(endTime, "yyyy-MM-dd'T'HH:mm");

        List<Doctor> doctors = userService.getAvailableDoctors(startDate, endDate, departmentId);

        request.setAttribute("startTime", startTime);
        request.setAttribute("endTime", endTime);
        request.setAttribute("selectedDepartment", departmentId);
        request.setAttribute("departments", departments);
        request.setAttribute("doctors", doctors);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("new_appointment.jsp");
        requestDispatcher.include(request, response);
    }

    private void addNewAppointments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User currentPatient = (User) session.getAttribute("currentUser");

        String startTime = Utils.getParameter(request, "startTime");
        Date startDate = Utils.toDate(startTime, "yyyy-MM-dd'T'HH:mm");

        String selectedDoctorID = request.getParameter("doctorID");

        try {
            appointmentService.addNewAppointments(currentPatient, selectedDoctorID, startDate, TreatmentType.OUTPATIENT);
        } catch (Exception e) {
            showMessage(request, response, e.getMessage());
        }
    }

    private void showMessage(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.getSession(false);
        request.setAttribute("message", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("new_appointment.jsp");
        requestDispatcher.forward(request, response);
    }
}
