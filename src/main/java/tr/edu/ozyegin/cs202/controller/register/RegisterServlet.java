package tr.edu.ozyegin.cs202.controller.register;

import tr.edu.ozyegin.cs202.service.auth.AuthenticationService;
import tr.edu.ozyegin.cs202.util.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = "/auth/register")
public class RegisterServlet extends HttpServlet {

    private AuthenticationService authenticationService = new AuthenticationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("register.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userId = Utils.getParameter(request, "userId");
        String firstName = Utils.getParameter(request, "firstName");
        String lastName = Utils.getParameter(request, "lastName");
        String password = Utils.getParameter(request, "password");

        try {
            boolean success = authenticationService.register(userId, firstName, lastName, password);
            if (success) {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("login");
                requestDispatcher.forward(request, response);
            } else {
                showError(request, response, "An unknown error has occurred.");
            }
        } catch (Exception e) {
            showError(request, response, e.getMessage());
        }
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        request.getSession(false).invalidate();
        request.setAttribute("error", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("register.jsp");
        requestDispatcher.forward(request, response);
    }
}
