<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>403 Forbidden</title>
    <%@ include file="/pages/common-styles.jspf" %>
    <style>
        .error-code  { font-size: 80px; font-weight: 800; color: #c0392b; line-height: 1; margin-bottom: 8px; }
        .error-title { font-size: 20px; font-weight: bold; color: #111; margin-bottom: 8px; }
        .error-desc  { color: #666; font-size: 14px; margin-bottom: 24px; line-height: 1.6; }
    </style>
</head>
<body>
<nav>
    <span class="brand">Student Manager</span>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_students">Students</a>
    <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
</nav>

<div class="container" style="max-width:520px;">
    <div class="card" style="text-align:center; padding:48px 32px;">
        <div class="error-code">403</div>
        <div class="error-title">Forbidden</div>
        <p class="error-desc">
            You do not have permission to access this page.<br>
            This section is restricted to administrators.
        </p>
        <a href="${pageContext.request.contextPath}/controller?command=show_all_students"
           class="btn btn-primary">Back to Home</a>
    </div>
</div>
</body>
</html>
