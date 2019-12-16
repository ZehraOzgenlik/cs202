package tr.edu.ozyegin.cs202.controller.home.manager.patientstatistic;

import tr.edu.ozyegin.cs202.model.Appointment;
import tr.edu.ozyegin.cs202.service.appointment.AppointmentService;
import tr.edu.ozyegin.cs202.util.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "PatientStatisticsServlet", urlPatterns = "/patient_statistics")
public class PatientStatisticsServlet extends HttpServlet {

    private AppointmentService appointmentService = new AppointmentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Date startTime = Utils.toDate(request.getParameter("start_time"));
        Date endTime = Utils.toDate(request.getParameter("end_time"));

        List<Appointment> appointments = appointmentService.getAppointments(startTime, endTime);

        request.setAttribute("appointments", appointments);
        request.setAttribute("start_time", Utils.toString(startTime));
        request.setAttribute("end_time", Utils.toString(endTime));

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("patient_statistics.jsp");
        requestDispatcher.include(request, response);
    }
}
