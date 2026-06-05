<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <%@ include file="/pages/common-styles.jspf" %>
</head>
<body>

<nav>
    <span class="brand">Student Manager</span>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_students">Students</a>
    <% if ("ADMIN".equals(session.getAttribute("user_role"))) { %>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_users">Users</a>
    <% } %>
    <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
</nav>

<div class="container" style="max-width:560px;">
    <div class="card" style="text-align:center; padding:48px 32px;">
        <h2>Welcome, ${sessionScope.user_name}</h2>
        <p class="text-muted mt-8">
            Role: <strong>${sessionScope.user_role}</strong>
        </p>
        <div style="display:flex; gap:12px; justify-content:center; margin-top:28px;">
            <a href="${pageContext.request.contextPath}/controller?command=show_all_students"
               class="btn btn-primary">Students</a>
            <% if ("ADMIN".equals(session.getAttribute("user_role"))) { %>
            <a href="${pageContext.request.contextPath}/controller?command=show_all_users"
               class="btn btn-primary">Users</a>
            <% } %>
        </div>
    </div>
</div>

</body>
</html>
