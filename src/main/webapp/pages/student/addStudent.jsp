<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<html>
<head>
    <title>Add student</title>
</head>
<body>
<h2>Add new student</h2>
<p style="color:red;">${errorMsg}</p>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="ADD_STUDENT"/>
    <label>Name: <input type="text" name="name" required/></label><br/>
    <label>Surname: <input type="text" name="surname" required/></label><br/>
    <label>Group ID: <input type="number" name="groupId" required/></label><br/>
    <input type="submit" value="Create student"/>
</form>
<p><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_STUDENTS">Back to students</a></p>
</body>
</html>