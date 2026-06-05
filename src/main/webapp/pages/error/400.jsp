<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>400 Bad Request</title>
    <%@ include file="/pages/common-styles.jspf" %>
    <%@ include file="/pages/error/error-layout.jspf" %>
</head>
<body>
<nav>
    <span class="brand">Student Manager</span>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_students">Students</a>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_users">Users</a>
    <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
</nav>

<div class="container" style="max-width:520px;">
    <div class="card" style="text-align:center; padding:48px 32px;">
        <div class="error-code" style="color:#d68910;">400</div>
        <div class="error-title">Bad Request</div>
        <p class="error-desc">The server could not understand the request.<br>Please check the submitted data and try again.</p>

        <% String msg = (String) request.getAttribute("errorMsg"); %>
        <% if (msg != null) { %>
        <div class="alert alert-danger" style="text-align:left; margin-bottom:20px;"><%= msg %></div>
        <% } %>

        <a href="${pageContext.request.contextPath}/controller?command=show_all_students" class="btn btn-primary">Back to Home</a>
    </div>
</div>
</body>
</html>
