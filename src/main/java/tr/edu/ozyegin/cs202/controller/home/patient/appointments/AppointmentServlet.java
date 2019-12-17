package tr.edu.ozyegin.cs202.controller.home.patient.appointments;

import tr.edu.ozyegin.cs202.model.Appointment;
import tr.edu.ozyegin.cs202.model.User;
import tr.edu.ozyegin.cs202.service.appointment.AppointmentService;
import tr.edu.ozyegin.cs202.util.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AppointmentServlet", urlPatterns = {"/patient/appointments, /doctor/appointments"})
public class AppointmentServlet extends HttpServlet {

    private AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");
        int timeLineCode = (int) session.getAttribute("time_line_code");

        try {
            showAppointment(req, resp, user, timeLineCode);
        } catch (ParseException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");
        int timeLineCode = (int) session.getAttribute("time_line_code");

        if (req.getParameter("cancel_appointments_button") != null) {
            String[] selections = req.getParameterValues("selectedDoctors");

            try {
                appointmentService.deleteSelectedAppointment(selections);
                showAppointment(req, resp, user, timeLineCode);
            } catch (ParseException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        } else if (req.getParameter("filter_appointments_button") != null) {
            String selectedDepartment = req.getParameter("departments");
            String startDate = req.getParameter("date_picker_start"); // Convert to Date
            String endDate = req.getParameter("date_picker_end"); // Convert to Date
            try {
                filterAppointments(req, resp, user, Utils.toDate(startDate), Utils.toDate(endDate), selectedDepartment, timeLineCode);
            } catch (ParseException | SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid button is pressed");
        }
    }

    private void showAppointment(HttpServletRequest req, HttpServletResponse resp, User user, int timeCode) throws IOException, ServletException, ParseException, SQLException, ClassNotFoundException {
        List<Appointment> appointmentList = appointmentService.getAppointmentList(user, timeCode);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("appointments.jsp");
        req.setAttribute("appointmentList", appointmentList);
        requestDispatcher.include(req, resp);
    }

    private void filterAppointments(HttpServletRequest req, HttpServletResponse resp, User user, Date startDate, Date endDate, String department, int timeCode) throws ServletException, IOException, ParseException, SQLException, ClassNotFoundException {
        List<Appointment> appointmentList = appointmentService.getFilteredAppointments(user, startDate,endDate,department,timeCode);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("appointments.jsp");
        req.setAttribute("appointmentList", appointmentList);
        requestDispatcher.include(req, resp);
    }

}
