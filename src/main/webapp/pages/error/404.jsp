<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 Not Found</title>
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
        <div class="error-code" style="color:#1a1a2e;">404</div>
        <div class="error-title">Page Not Found</div>
        <p class="error-desc">The page you are looking for does not exist or has been moved.<br>Check the URL or return to the home page.</p>

        <div class="actions" style="justify-content:center;">
            <a href="${pageContext.request.contextPath}/controller?command=show_all_students" class="btn btn-primary">Back to Home</a>
            <a href="javascript:history.back()" class="btn btn-warning">Previous Page</a>
        </div>
    </div>
</div>
</body>
</html>
