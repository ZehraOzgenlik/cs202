package tr.edu.ozyegin.cs202.controller.patientlist;

import tr.edu.ozyegin.cs202.service.model.Patient;
import tr.edu.ozyegin.cs202.service.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PatientListServlet", urlPatterns = "/patient_list")
public class PatientListServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List<Patient> patients = userService.getPatients();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("patient_list.jsp");
        request.setAttribute("patients", patients);

        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
