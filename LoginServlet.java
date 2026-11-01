package com.lms.servlet;

import com.lms.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uid = request.getParameter("uid");
        String password = request.getParameter("password");
        boolean isValid = userDAO.validateUser(uid, password);
        if (isValid) {
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedInUser", uid.trim());
            response.sendRedirect(request.getContextPath() + "/CourseServlet");
        } else {
            request.setAttribute("errorMessage", "Invalid User ID or Password. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
