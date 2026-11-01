<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Automatically initializes the default user session ("admin") and redirects directly
    // to CourseServlet (Course Dashboard) so the login step is skipped for now.
    if (session.getAttribute("loggedInUser") == null) {
        session.setAttribute("loggedInUser", "admin");
    }
    response.sendRedirect(request.getContextPath() + "/CourseServlet");
%>
