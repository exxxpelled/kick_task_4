<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
</head>
<body>
<h2>Sign in</h2>
<form action="${pageContext.request.contextPath}/controller" method="post">
  <input type="hidden" name="command" value="LOGIN"/>
  <label>Login: <input type="text" name="login" required/></label><br/>
  <label>Password: <input type="password" name="password" required/></label><br/>
  <input type="submit" value="Log in"/>
</form>
<p style="color:red;">${login_msg}</p>
</body>
</html>