<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="auth/login.jsp"/>
</c:if>
<html>
<head>
    <title>Main</title>
</head>
<body>
<h2>Welcome, ${sessionScope.user_name}!</h2>
<ul>
    <li><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_USERS">Manage Users</a></li>
    <li><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_GROUPS">Manage Groups</a></li>
    <li><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_STUDENTS">Manage Students</a></li>
</ul>
<p><a href="${pageContext.request.contextPath}/controller?command=LOGOUT">Log out</a></p>
</body>
</html>