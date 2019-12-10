package tr.edu.ozyegin.cs202.controller.login;

import tr.edu.ozyegin.cs202.service.login.LoginService;
import tr.edu.ozyegin.cs202.service.login.model.User;

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

    private LoginService loginService = new LoginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = request.getParameter("user_id");
        String password = request.getParameter("password");

        if (!userId.isEmpty() && !password.isEmpty()) {
            User user = loginService.login(userId, password);

            if (user != null) {
                showSuccess(request, response, user);
            } else {
                showError(request, response, "Invalid user id or password.");
            }
        } else {
            showError(request, response, "User id and password cannot be empty.");
        }
    }

    private void showSuccess(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.setAttribute("user", user);
        response.sendRedirect("home.jsp");
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.getSession(false).invalidate();
        request.setAttribute("error", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("login.jsp");
        requestDispatcher.forward(request, response);
    }
}
