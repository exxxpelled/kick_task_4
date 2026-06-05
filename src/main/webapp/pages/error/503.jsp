<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>503 Service Unavailable</title>
    <%@ include file="/pages/common-styles.jspf" %>
    <%@ include file="/pages/error/error-layout.jspf" %>
</head>
<body>
<nav>
    <span class="brand">Student Manager</span>
</nav>

<div class="container" style="max-width:520px;">
    <div class="card" style="text-align:center; padding:48px 32px;">
        <div class="error-code" style="color:#7d3c98;">503</div>
        <div class="error-title">Service Unavailable</div>
        <p class="error-desc">The service is temporarily unavailable.<br>This is usually a database or connection issue.<br>Please try again in a moment.</p>

        <a href="${pageContext.request.contextPath}/controller?command=show_all_students" class="btn btn-primary">Try Again</a>
    </div>
</div>
</body>
</html>
