<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <style>
        *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }
        body {
            font-family: Arial, sans-serif;
            font-size: 14px;
            background: #f4f5f7;
            display: flex;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }
        .card {
            background: #fff;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 36px 32px;
            width: 340px;
        }
        h2 { font-size: 18px; font-weight: bold; margin-bottom: 24px; color: #111; }
        .form-group { margin-bottom: 14px; }
        .form-group label { display: block; margin-bottom: 4px; font-weight: bold; color: #333; }
        .form-group input {
            width: 100%; padding: 8px 10px;
            border: 1px solid #ccc; border-radius: 3px; font-size: 14px;
        }
        .form-group input:focus { outline: none; border-color: #1a1a2e; }
        .form-hint { font-size: 12px; color: #888; margin-top: 3px; }
        .btn {
            display: inline-block; padding: 8px 16px; font-size: 13px;
            border: 1px solid transparent; border-radius: 3px; cursor: pointer;
            text-decoration: none; background: none;
        }
        .btn-primary { background: #1a1a2e; color: #fff; border-color: #1a1a2e; width: 100%; }
        .btn-primary:hover { background: #2a2a4a; }
        .alert {
            padding: 10px 14px; border-radius: 3px; margin-bottom: 16px; font-size: 13px;
            border: 1px solid transparent;
        }
        .alert-danger  { background: #fdedec; color: #c0392b; border-color: #f1948a; }
        .alert-success { background: #eafaf1; color: #1e8449; border-color: #a9dfbf; }
        .login-link { margin-top: 16px; text-align: center; font-size: 13px; color: #555; }
        .login-link a { color: #1a1a2e; }
    </style>
</head>
<body>
<div class="card">
    <h2>Create Account</h2>

    <% String errorMsg   = (String) request.getAttribute("errorMsg"); %>
    <% String successMsg = (String) request.getAttribute("successMsg"); %>
    <% String prefill    = (String) request.getAttribute("prefillLogin"); %>

    <% if (errorMsg   != null) { %><div class="alert alert-danger"><%= errorMsg %></div><% } %>
    <% if (successMsg != null) { %><div class="alert alert-success"><%= successMsg %></div><% } %>

    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="register"/>

        <div class="form-group">
            <label for="login">Login</label>
            <input type="text" id="login" name="login"
                   value="<%= prefill != null ? prefill : "" %>"
                   minlength="3" maxlength="30" required autofocus/>
            <span class="form-hint">3–30 characters, letters, digits, _ . -</span>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password"
                   minlength="4" maxlength="50" required/>
            <span class="form-hint">Minimum 4 characters.</span>
        </div>

        <button type="submit" class="btn btn-primary">Register</button>
    </form>

    <div class="login-link">
        Already have an account? <a href="${pageContext.request.contextPath}/pages/auth/login.jsp">Sign in</a>
    </div>
</div>
</body>
</html>
