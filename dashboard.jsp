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
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Course Dashboard - Learning Management System</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/dashboard.css">
</head>
<body>

<header class="navbar">
    <div class="nav-brand">
        <span class="brand-icon">&#127891;</span>
        <span class="brand-title">Learning Management System</span>
    </div>

    <div class="nav-right">
        <span class="welcome-text">Welcome, <strong><%= loggedInUser %></strong></span>
        <a href="<%= request.getContextPath() %>/AddContentServlet" class="add-topic-nav-btn">+ Add Topic Content</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet" class="logout-btn">Logout</a>
    </div>
</header>

<main class="dashboard-container">
    <section class="dashboard-banner">
        <div>
            <h1>Available Programming Courses</h1>
            <p>Select any course below to explore structured topics or add new course content dynamically.</p>
        </div>
        <a href="<%= request.getContextPath() %>/AddContentServlet" class="banner-add-btn">+ Add Topic to Course</a>
    </section>

    <section class="course-grid">
        <% if (courseList != null && !courseList.isEmpty()) {
            for (Course course : courseList) { %>
                <article class="course-card">
                    <div class="card-top">
                        <span class="course-badge">Course #<%= course.getCourseId() %></span>
                        <span class="level-badge"><%= course.getCourseLevel() %></span>
                    </div>

                    <h2 class="course-title"><%= course.getCourseName() %></h2>
                    <p class="course-desc"><%= course.getCourseDescription() %></p>

                    <div class="course-meta">
                        <span><strong>Duration:</strong> <%= course.getCourseDuration() %></span>
                        <span><strong>Difficulty:</strong> <%= course.getCourseLevel() %></span>
                        <span><strong>Topics:</strong> <%= (course.getContents() != null ? course.getContents().size() : 0) %> Modules</span>
                    </div>

                    <div class="card-actions">
                        <form action="<%= request.getContextPath() %>/CourseServlet" method="get" class="card-action-form">
                            <input type="hidden" name="action" value="details">
                            <input type="hidden" name="id" value="<%= course.getCourseId() %>">
                            <button type="submit" class="view-course-btn">View Course</button>
                        </form>
                        <a href="<%= request.getContextPath() %>/AddContentServlet?courseId=<%= course.getCourseId() %>" class="card-add-link" title="Add Topic to <%= course.getCourseName() %>">+ Add Topic</a>
                    </div>
                </article>
        <%  }
        } else { %>
            <div class="empty-state">
                <p>No courses found in the database.</p>
            </div>
        <% } %>
    </section>
</main>

<footer class="footer">
    <p>&copy; 2026 Learning Management System | Built with JSP, Servlets, Hibernate &amp; Oracle Database</p>
</footer>

</body>
</html>
