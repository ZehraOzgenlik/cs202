package tr.edu.ozyegin.cs202.controller.login;

import tr.edu.ozyegin.cs202.model.User;
import tr.edu.ozyegin.cs202.service.auth.AuthenticationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        try {
            User user = authenticationService.login(userId, password);
            redirectToHome(request, response, user);
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }
    }

    private void redirectToHome(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.setAttribute("currentUser", user);
        switch (user.getUserType()) {
            case PATIENT:
                response.sendRedirect("home_patient.jsp");
                break;
            case DOCTOR:
                response.sendRedirect("home_doctor.jsp");
                break;
            case NURSE:
                response.sendRedirect("home_nurse.jsp");
                break;
            case MANAGER:
                response.sendRedirect("home_manager.jsp");
                break;
        }
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.getSession(false).invalidate();
        request.setAttribute("error", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(request, response);
    }
}
