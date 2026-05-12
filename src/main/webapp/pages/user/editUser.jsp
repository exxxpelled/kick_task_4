<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.kick_4.dao.impl.UserDaoImpl, com.example.kick_4.entity.User, java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<%
    String userIdParam = request.getParameter("userId");
    User user = null;
    if (userIdParam != null && !userIdParam.isBlank()) {
        try {
            long id = Long.parseLong(userIdParam);
            List<User> all = UserDaoImpl.getInstance().findAll();
            for (User u : all) {
                if (u.getId() == id) {
                    user = u;
                    break;
                }
            }
        } catch (Exception e) {
        }
    }
    if (user == null && request.getAttribute("javax.servlet.forward.request_uri") == null) {
        response.sendRedirect(request.getContextPath() + "/controller?command=SHOW_ALL_USERS");
        return;
    }
    request.setAttribute("editUser", user);
%>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
<h2>Edit user</h2>
<p style="color:red;">${errorMsg}</p>
<c:if test="${not empty editUser}">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="CHANGE_USER"/>
        <input type="hidden" name="userId" value="${editUser.id}"/>
        <label>Login: <input type="text" name="login" value="${editUser.login}" required/></label><br/>
        <label>New password: <input type="password" name="password" placeholder="leave blank to keep current"/></label><br/>
        <input type="submit" value="Update user"/>
    </form>
</c:if>
<c:if test="${empty editUser}">
    <p>User not found.</p>
</c:if>
<p><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_USERS">Back to users</a></p>
</body>
</html>