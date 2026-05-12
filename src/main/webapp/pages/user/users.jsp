<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h2>Users</h2>
<p style="color:green;">${successMsg}</p>
<p style="color:red;">${errorMsg}</p>
<table border="1">
    <tr><th>ID</th><th>Login</th><th>Password</th><th>Actions</th></tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.login}</td>
            <td>${user.password}</td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?command=DELETE_USER&userId=${user.id}">Delete</a>
                <a href="editUser.jsp?userId=${user.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="addUser.jsp">Add new user</a></p>
<p><a href="${pageContext.request.contextPath}/controller?command=LOGOUT">Log out</a> | <a href="../main.jsp">Main page</a></p>
</body>
</html>