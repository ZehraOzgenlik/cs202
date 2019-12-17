package tr.edu.ozyegin.cs202.controller.home.patient;


import tr.edu.ozyegin.cs202.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "PatientServlet", urlPatterns = "/patients")
public class PatientServlet extends HttpServlet {

    private static final int PAST_APPOINTMENTS = 1;
    private static final int FUTURE_APPOINTMENTS = 2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        if (req.getParameter("past_appointments") != null) {
            session.setAttribute("time_line_code", PAST_APPOINTMENTS);
            req.getRequestDispatcher("appointments").forward(req, resp);
        } else if (req.getParameter("make_new_appointment") != null) {
            //todo forward request another page or servlet to handle making new reservation
            // makeNewAppointment(req, resp, user);
        } else if (req.getParameter("future_appointments") != null) {
            session.setAttribute("time_line_code", FUTURE_APPOINTMENTS);
            req.getRequestDispatcher("appointments").forward(req, resp);
        } else {
            System.out.println("Error: PatientServlet : doGet : 46");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }


}
