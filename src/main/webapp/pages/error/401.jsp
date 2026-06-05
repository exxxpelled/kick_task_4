<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>401 Unauthorized</title>
    <%@ include file="/pages/common-styles.jspf" %>
    <%@ include file="/pages/error/error-layout.jspf" %>
</head>
<body>
<nav>
    <span class="brand">Student Manager</span>
</nav>

<div class="container" style="max-width:520px;">
    <div class="card" style="text-align:center; padding:48px 32px;">
        <div class="error-code" style="color:#d68910;">401</div>
        <div class="error-title">Unauthorized</div>
        <p class="error-desc">You must be logged in to access this page.<br>Please sign in and try again.</p>

        <a href="${pageContext.request.contextPath}/pages/auth/login.jsp" class="btn btn-primary">Go to Login</a>
    </div>
</div>
</body>
</html>
