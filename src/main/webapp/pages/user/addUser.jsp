<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<html>
<head>
    <title>Add user</title>
</head>
<body>
<h2>Add new user</h2>
<p style="color:red;">${errorMsg}</p>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="ADD_USER"/>
    <label>Login: <input type="text" name="login" required/></label><br/>
    <label>Password: <input type="password" name="password" required/></label><br/>
    <input type="submit" value="Create user"/>
</form>
<p><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_USERS">Back to users</a></p>
</body>
</html>