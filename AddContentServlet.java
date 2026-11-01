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

@WebServlet("/AddContentServlet")
public class AddContentServlet extends HttpServlet {
    private final CourseDAO courseDAO = new CourseDAO();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        if (session.getAttribute("loggedInUser") == null) {
            session.setAttribute("loggedInUser", "admin");
        }

        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courseList", courses);

        String preselectedCourseId = request.getParameter("courseId");
        if (preselectedCourseId != null) {
            request.setAttribute("selectedCourseId", preselectedCourseId);
        }

        request.getRequestDispatcher("add-content.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String courseIdStr = request.getParameter("courseId");
        String topicTitle = request.getParameter("topicTitle");
        String topicDescription = request.getParameter("topicDescription");

        try {
            int courseId = Integer.parseInt(courseIdStr);
            boolean added = courseDAO.addCourseContent(courseId, topicTitle, topicDescription);

            if (added) {
                // Redirect straight to the course details page so the user sees the newly added topic
                response.sendRedirect(request.getContextPath() + "/CourseServlet?action=details&id=" + courseId + "&added=true");
                return;
            } else {
                request.setAttribute("errorMessage", "Please provide both Topic Title and Topic Description.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Invalid course selection.");
        }

        List<Course> courses = courseDAO.getAllCourses();
        request.setAttribute("courseList", courses);
        request.getRequestDispatcher("add-content.jsp").forward(request, response);
    }
}
