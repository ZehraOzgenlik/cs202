package tr.edu.ozyegin.cs202.controller.home.doctor.restdays;

import tr.edu.ozyegin.cs202.model.RestDay;
import tr.edu.ozyegin.cs202.model.User;
import tr.edu.ozyegin.cs202.service.restday.RestDayService;
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

@WebServlet(name = "RestDaysServlet", urlPatterns = {"/doctor/rest_days", "/doctor/home_doctor.jsp"})
public class RestDaysServlet extends HttpServlet {

    private RestDayService restDayService = new RestDayService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        showRestDays(request, response, user);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        String eventId = request.getParameter("eventId");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        Date startDate = Utils.toDate(startTime, "yyyy-MM-dd'T'HH:mm");
        Date endDate = Utils.toDate(endTime, "yyyy-MM-dd'T'HH:mm");

        try {
            if (request.getParameter("update") != null && (eventId == null || eventId.length() == 0)) {
                restDayService.saveRestDay(user, startDate, endDate);
                request.setAttribute("message", "The record saved successfully");
            } else if (request.getParameter("update") != null) {
                restDayService.updateRestDay(user, Integer.parseInt(eventId), startDate, endDate);
                request.setAttribute("message", "The record updated successfully");
            } else if (request.getParameter("delete") != null) {
                restDayService.deleteRestDay(user, Integer.parseInt(eventId), startDate, endDate);
                request.setAttribute("message", "The record deleted successfully");
            }

            showRestDays(request, response, user);
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }
    }

    private void showRestDays(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, ServletException {
        List<RestDay> restDays = restDayService.getRestDays(user);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("rest_days.jsp");
        request.setAttribute("restDays", restDays);
        requestDispatcher.include(request, response);
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.getSession(false).invalidate();
        request.setAttribute("message", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("rest_days.jsp");
        requestDispatcher.forward(request, response);
    }
}
