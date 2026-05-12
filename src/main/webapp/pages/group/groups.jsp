<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<html>
<head>
    <title>Groups</title>
</head>
<body>
<h2>Groups</h2>
<p style="color:green;">${successMsg}</p>
<p style="color:red;">${errorMsg}</p>
<table border="1">
    <tr><th>ID</th><th>Name</th><th>Actions</th></tr>
    <c:forEach var="group" items="${groups}">
        <tr>
            <td>${group.id}</td>
            <td>${group.name}</td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?command=DELETE_GROUP&groupId=${group.id}">Delete</a>
                <a href="editGroup.jsp?groupId=${group.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="addGroup.jsp">Add new group</a></p>
<p><a href="../main.jsp">Main page</a> | <a href="${pageContext.request.contextPath}/controller?command=LOGOUT">Log out</a></p>
</body>
</html>