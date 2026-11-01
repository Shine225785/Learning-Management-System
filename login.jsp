<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Learning Management System</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
</head>
<body>

<div class="login-wrapper">
    <div class="login-card">
        <div class="brand-badge">LMS PORTAL</div>
        <h1 class="project-heading">Learning Management System</h1>
        <p class="login-subtitle">Sign in with your User ID and Password to access courses</p>

        <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
            <div class="error-box" role="alert">
                <%= errorMessage %>
            </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/LoginServlet" method="post" autocomplete="off">
            <div class="form-group">
                <label for="uid">User ID</label>
                <input type="text" id="uid" name="uid" value="" placeholder="Enter your User ID (e.g. admin)" required autofocus>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" value="" placeholder="Enter your Password" required>
            </div>

            <button type="submit" class="login-btn">Login</button>
        </form>

        <div class="skip-login-box">
            <a href="<%= request.getContextPath() %>/CourseServlet" class="skip-link">
                Skip Login &amp; Go Directly to Course Dashboard &rarr;
            </a>
        </div>

        <div class="demo-credentials">
            <span>Sample Credentials: <strong>UID:</strong> admin | <strong>Password:</strong> admin123</span>
        </div>
    </div>
</div>

</body>
</html>
