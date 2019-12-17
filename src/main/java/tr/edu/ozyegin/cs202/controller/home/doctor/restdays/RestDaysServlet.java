package tr.edu.ozyegin.cs202.controller.home.doctor.restdays;

import tr.edu.ozyegin.cs202.model.RestDay;
import tr.edu.ozyegin.cs202.model.User;
import tr.edu.ozyegin.cs202.service.resday.RestDayService;

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
import java.util.List;

@WebServlet(name = "RestDaysServlet", urlPatterns = "/doctor/restDays")

public class RestDaysServlet extends HttpServlet {

    private RestDayService restDayService = new RestDayService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        try {
            showRestDays(req, resp, user);
        } catch (ParseException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(true);
        User user = (User) session.getAttribute("currentUser");

        if (req.getParameter("cancel_rest_day_button") != null) {
            String[] daySelections = req.getParameterValues("selectedRestDays");

            try {
                restDayService.deleteSelectedRestDays(daySelections);
                showRestDays(req, resp, user);
            } catch (ParseException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        }else if(req.getParameter("add_new_rest_day_button") != null){

            //todo forward to page to add new rest day. This page is not created yet.

        }
    }

    private void showRestDays(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException, ServletException, ParseException, SQLException, ClassNotFoundException {
        List<RestDay> restDayList = restDayService.getRestDays(user);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("rest_days.jsp");
        req.setAttribute("restDayList", restDayList);
        requestDispatcher.include(req, resp);
    }
}
