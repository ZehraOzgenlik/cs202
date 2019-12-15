package tr.edu.ozyegin.cs202.controller.home.manager.doctorlist;

import tr.edu.ozyegin.cs202.service.model.Doctor;
import tr.edu.ozyegin.cs202.service.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DoctorListServlet", urlPatterns = "/doctor_list")
public class DoctorListServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        List<Doctor> doctors = userService.getDoctors();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("doctor_list.jsp");
        request.setAttribute("doctors", doctors);

        requestDispatcher.include(request, response);
    }
}
