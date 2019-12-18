package tr.edu.ozyegin.cs202.controller.home.patient.newappointment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PatientServlet", urlPatterns = {"/patient/new_appointment", "/patient/home_patient.jsp"})
public class NewAppointmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("new_appointment.jsp");
        requestDispatcher.include(request, response);
        // TODO
    }
}
