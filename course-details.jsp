<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lms.entity.Course" %>
<%@ page import="com.lms.entity.CourseContent" %>
<%@ page import="com.lms.dao.CourseDAO" %>
<%@ page import="java.util.List" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    String loggedInUser = (String) session.getAttribute("loggedInUser");
    if (loggedInUser == null) {
        loggedInUser = "admin";
        session.setAttribute("loggedInUser", loggedInUser);
    }

    Course course = (Course) request.getAttribute("course");
    if (course == null) {
        String idParam = request.getParameter("id");
        int courseId = 3;
        if (idParam != null) {
            try {
                courseId = Integer.parseInt(idParam);
            } catch (NumberFormatException ignored) {}
        }
        CourseDAO dao = new CourseDAO();
        course = dao.getCourseById(courseId);
    }

    String addedFlag = request.getParameter("added");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= (course != null ? course.getCourseName() : "Course Details") %> - LMS</title>
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
        <% if (course != null) { %>
            <a href="<%= request.getContextPath() %>/AddContentServlet?courseId=<%= course.getCourseId() %>" class="add-nav-btn">+ Add Topic</a>
        <% } %>
        <a href="<%= request.getContextPath() %>/CourseServlet" class="back-nav-btn">&larr; Back to Courses</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet" class="logout-btn">Logout</a>
    </div>
</header>

<main class="course-container">
    <% if (course != null) { %>

        <% if ("true".equals(addedFlag)) { %>
            <div class="alert-success">
                &#10004; New course topic has been added successfully!
            </div>
        <% } %>

        <section class="course-header-card">
            <div class="header-badges">
                <span class="badge-duration">&#128337; Duration: <%= course.getCourseDuration() %></span>
                <span class="badge-level">&#128202; Level: <%= course.getCourseLevel() %></span>
            </div>
            <h1 class="course-main-title"><%= course.getCourseName() %></h1>
            <p class="course-main-desc"><%= course.getCourseDescription() %></p>

            <div class="header-actions">
                <a href="<%= request.getContextPath() %>/CourseServlet" class="back-btn">&larr; Back to Courses</a>
                <a href="<%= request.getContextPath() %>/AddContentServlet?courseId=<%= course.getCourseId() %>" class="add-header-btn">+ Add New Topic to <%= course.getCourseName() %></a>
            </div>
        </section>

        <section class="topics-section">
            <div class="topics-header-row">
                <h2 class="section-heading">Course Curriculum &amp; Topics (<%= (course.getContents() != null ? course.getContents().size() : 0) %>)</h2>
                <a href="#quick-add-topic" class="jump-add-link">+ Quick Add Topic Below &darr;</a>
            </div>

            <div class="topics-list">
                <%
                    List<CourseContent> topics = course.getContents();
                    if (topics != null && !topics.isEmpty()) {
                        int index = 1;
                        for (CourseContent topic : topics) {
                %>
                    <article class="topic-card">
                        <div class="topic-number"><%= index++ %></div>
                        <div class="topic-body">
                            <h3 class="topic-title"><%= topic.getTopicTitle() %></h3>
                            <p class="topic-description"><%= topic.getTopicDescription() %></p>
                        </div>
                    </article>
                <%
                        }
                    } else {
                %>
                    <p class="empty-topics">No topics available for this course yet.</p>
                <% } %>
            </div>
        </section>

        <section id="quick-add-topic" class="quick-add-section">
            <h3 class="quick-add-title">Add Another Topic to <%= course.getCourseName() %></h3>
            <form action="<%= request.getContextPath() %>/AddContentServlet" method="post" class="topic-form">
                <input type="hidden" name="courseId" value="<%= course.getCourseId() %>">

                <div class="form-group">
                    <label for="topicTitle">Topic Title</label>
                    <input type="text" id="topicTitle" name="topicTitle" placeholder="Enter new topic title..." maxlength="150" required>
                </div>

                <div class="form-group">
                    <label for="topicDescription">Topic Explanation</label>
                    <textarea id="topicDescription" name="topicDescription" rows="3" placeholder="Enter short and simple explanation for this topic..." maxlength="1000" required></textarea>
                </div>

                <button type="submit" class="submit-topic-btn">+ Add Topic to <%= course.getCourseName() %></button>
            </form>
        </section>

        <div class="bottom-navigation">
            <a href="<%= request.getContextPath() %>/CourseServlet" class="back-btn">&larr; Back to Courses</a>
            <a href="<%= request.getContextPath() %>/LogoutServlet" class="logout-secondary-btn">Logout</a>
        </div>
    <% } else { %>
        <section class="course-header-card">
            <h1>Course Not Found</h1>
            <p>The requested course could not be found.</p>
            <a href="<%= request.getContextPath() %>/CourseServlet" class="back-btn">&larr; Back to Courses</a>
        </section>
    <% } %>
</main>

<footer class="footer">
    <p>&copy; 2026 Learning Management System | Dynamic JSP Course Player</p>
</footer>

</body>
</html>
