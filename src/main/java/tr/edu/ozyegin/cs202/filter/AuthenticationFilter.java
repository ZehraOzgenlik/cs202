package tr.edu.ozyegin.cs202.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String contextPath = request.getContextPath();

        boolean loggedIn = session != null && session.getAttribute("currentUser") != null;
        boolean loginRequest = request.getRequestURI().equals(contextPath + "/login");
        boolean registerRequest = request.getRequestURI().equals(contextPath + "/register");

        if (loggedIn || loginRequest || registerRequest) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(contextPath + "/login");
        }
    }
}
