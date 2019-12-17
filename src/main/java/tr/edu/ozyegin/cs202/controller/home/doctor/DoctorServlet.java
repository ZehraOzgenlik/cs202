package tr.edu.ozyegin.cs202.controller.home.doctor;

import tr.edu.ozyegin.cs202.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DoctorServlet", urlPatterns = "/doctor/doctors")
public class DoctorServlet extends HttpServlet {

    private static final int PAST_APPOINTMENTS = 1;
    private static final int FUTURE_APPOINTMENTS = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        if (req.getParameter("doctor_appointments_past_button") != null) {
            session.setAttribute("time_line_code", PAST_APPOINTMENTS);
            req.getRequestDispatcher("../patient/appointments.jsp").forward(req, resp);

        } else if (req.getParameter("doctor_appointments_future_button") != null) {
            session.setAttribute("time_line_code", FUTURE_APPOINTMENTS);
            req.getRequestDispatcher("../patient/appointments.jsp").forward(req, resp);

        } else if (req.getParameter("rest_days_button") != null) {
            req.getRequestDispatcher("restDays").forward(req, resp);

        } else if (req.getParameter("available_rooms_button") != null) {
            //todo forward roomServlet. (roomServlet and room_page are not created yet. room_page will show the available rooms. And allow doctor to select 1 room 1 patient.
            // Then make an appointment with selected ones.)
        } else {
            System.out.println("Invalid button pressed.");
        }
    }
}
