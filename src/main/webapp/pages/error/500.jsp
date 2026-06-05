<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 Internal Server Error</title>
    <%@ include file="/pages/common-styles.jspf" %>
    <%@ include file="/pages/error/error-layout.jspf" %>
    <style>
        .detail-box {
            background: #fdedec;
            border: 1px solid #f1948a;
            border-radius: 3px;
            padding: 10px 14px;
            text-align: left;
            font-family: monospace;
            font-size: 12px;
            color: #c0392b;
            margin-bottom: 20px;
            word-break: break-word;
        }
    </style>
</head>
<body>
<nav>
    <span class="brand">Student Manager</span>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_students">Students</a>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_users">Users</a>
    <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
</nav>

<div class="container" style="max-width:560px;">
    <div class="card" style="text-align:center; padding:48px 32px;">
        <div class="error-code" style="color:#c0392b;">500</div>
        <div class="error-title">Internal Server Error</div>
        <p class="error-desc">An unexpected error occurred on the server.<br>The issue has been logged. Please try again later.</p>

        <% String msg = (String) request.getAttribute("errorMsg"); %>
        <% if (msg != null) { %>
        <div class="detail-box"><%= msg %></div>
        <% } %>

        <% if (exception != null && exception.getMessage() != null) { %>
        <div class="detail-box"><%= exception.getMessage() %></div>
        <% } %>

        <a href="${pageContext.request.contextPath}/controller?command=show_all_students" class="btn btn-primary">Back to Home</a>
    </div>
</div>
</body>
</html>
