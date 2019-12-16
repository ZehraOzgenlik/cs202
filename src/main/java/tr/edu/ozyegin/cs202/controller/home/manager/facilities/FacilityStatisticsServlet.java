package tr.edu.ozyegin.cs202.controller.home.manager.facilities;

import tr.edu.ozyegin.cs202.model.Department;
import tr.edu.ozyegin.cs202.service.appointment.AppointmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "FacilityStatisticsServlet", urlPatterns = "/facility_statistics")
public class FacilityStatisticsServlet extends HttpServlet {

    private AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Map<Department, Integer> appointmentCounts = appointmentService.getAppointmentsCountsByDepartment();

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("facility_statistics.jsp");
        request.setAttribute("appointmentCounts", appointmentCounts);

        requestDispatcher.include(request, response);
    }
}
