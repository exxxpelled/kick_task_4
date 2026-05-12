<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<html>
<head>
    <title>Add group</title>
</head>
<body>
<h2>Add new group</h2>
<p style="color:red;">${errorMsg}</p>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="ADD_GROUP"/>
    <label>Name: <input type="text" name="groupName" required/></label><br/>
    <input type="submit" value="Create group"/>
</form>
<p><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_GROUPS">Back to groups</a></p>
</body>
</html>