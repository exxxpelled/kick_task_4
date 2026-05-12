<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<html>
<head>
    <title>Students</title>
</head>
<body>
<h2>Students</h2>
<p style="color:green;">${successMsg}</p>
<p style="color:red;">${errorMsg}</p>
<table border="1">
    <tr><th>ID</th><th>Name</th><th>Surname</th><th>Group ID</th><th>Actions</th></tr>
    <c:forEach var="student" items="${students}">
        <tr>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.surname}</td>
            <td>${student.groupId}</td>
            <td>
                <a href="${pageContext.request.contextPath}/controller?command=DELETE_STUDENT&studentId=${student.id}">Delete</a>
                <a href="editStudent.jsp?studentId=${student.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="addStudent.jsp">Add new student</a></p>
<p><a href="../main.jsp">Main page</a> | <a href="${pageContext.request.contextPath}/controller?command=LOGOUT">Log out</a></p>
</body>
</html>