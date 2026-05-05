<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 19/04/2026
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
    <p>Hello(forward) ${user}</p>
    <br/>
    <p>Hi(redirect/forward) ${user_name}</p>
    <br/>
    <form action="controller">
        <input type="hidden" name="command" value="logout">
        <input type="submit" value="logOut">
    </form>
</body>
</html>
