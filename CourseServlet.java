package com.lms.servlet;

import com.lms.dao.CourseDAO;
import com.lms.entity.Course;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {
    private final CourseDAO courseDAO = new CourseDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(true);
        if (session.getAttribute("loggedInUser") == null) {
            session.setAttribute("loggedInUser", "admin");
        }

        String action = request.getParameter("action");
        String courseIdParam = request.getParameter("id");

        if ("details".equalsIgnoreCase(action) && courseIdParam != null) {
            try {
                int courseId = Integer.parseInt(courseIdParam);
                Course selectedCourse = courseDAO.getCourseById(courseId);
                if (selectedCourse != null) {
                    request.setAttribute("course", selectedCourse);
                    request.getRequestDispatcher("course-details.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException ignored) {
            }
        }

        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courseList", courses);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
