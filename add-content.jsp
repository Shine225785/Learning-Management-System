<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.lms.entity.Course" %>
<%@ page import="com.lms.dao.CourseDAO" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    String loggedInUser = (String) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        loggedInUser = "admin";
        session.setAttribute("loggedInUser", loggedInUser);
    }

    @SuppressWarnings("unchecked")
    List<Course> courseList = (List<Course>) request.getAttribute("courseList");
    if (courseList == null) {
        CourseDAO dao = new CourseDAO();
        courseList = dao.getAllCourses();
    }

    String selectedCourseId = (String) request.getAttribute("selectedCourseId");
    if (selectedCourseId == null) {
        selectedCourseId = request.getParameter("courseId");
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Course Topic - Learning Management System</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/course.css">
</head>
<body>

<header class="navbar">
    <div class="nav-brand">
        <span class="brand-icon">&#127891;</span>
        <span class="brand-title">Learning Management System</span>
    </div>

    <div class="nav-right">
        <span class="welcome-text">Logged in as: <strong><%= loggedInUser %></strong></span>
        <a href="<%= request.getContextPath() %>/CourseServlet" class="back-nav-btn">&larr; Back to Dashboard</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet" class="logout-btn">Logout</a>
    </div>
</header>

<main class="course-container">
    <section class="form-card">
        <h1 class="form-heading">Add New Course Topic</h1>
        <p class="form-subtitle">Add a new syllabus topic and explanation to any course in the Oracle Database.</p>

        <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <div class="alert-error">
                <%= errorMessage %>
            </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/AddContentServlet" method="post" class="topic-form">
            <div class="form-group">
                <label for="courseId">Select Course</label>
                <select id="courseId" name="courseId" required>
                    <% if (courseList != null) {
                        for (Course c : courseList) {
                            boolean isSelected = String.valueOf(c.getCourseId()).equals(selectedCourseId);
                    %>
                        <option value="<%= c.getCourseId() %>" <%= isSelected ? "selected" : "" %>>
                            Course #<%= c.getCourseId() %> - <%= c.getCourseName() %> (<%= c.getCourseLevel() %>)
                        </option>
                    <%  }
                    } %>
                </select>
            </div>

            <div class="form-group">
                <label for="topicTitle">Topic Title</label>
                <input type="text" id="topicTitle" name="topicTitle"
                       placeholder="e.g. Multithreading and Concurrency"
                       maxlength="150" required>
            </div>

            <div class="form-group">
                <label for="topicDescription">Topic Explanation / Content</label>
                <textarea id="topicDescription" name="topicDescription" rows="5"
                          placeholder="Write a clear, beginner-friendly explanation for this topic..."
                          maxlength="1000" required></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="submit-topic-btn">+ Save Topic to Course</button>
                <a href="<%= request.getContextPath() %>/CourseServlet" class="cancel-btn">Cancel</a>
            </div>
        </form>
    </section>
</main>

<footer class="footer">
    <p>&copy; 2026 Learning Management System | Add Course Content Module</p>
</footer>

</body>
</html>
