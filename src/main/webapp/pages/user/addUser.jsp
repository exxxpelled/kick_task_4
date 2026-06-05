<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add User</title>
    <%@ include file="/pages/common-styles.jspf" %>
    <style>
        select {
            width: 100%; padding: 8px 10px;
            border: 1px solid #ccc; border-radius: 3px; font-size: 14px;
            background: #fff;
        }
        select:focus { outline: none; border-color: #1a1a2e; }
    </style>
</head>
<body>

<nav>
    <span class="brand">Student Manager</span>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_students">Students</a>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_users">Users</a>
    <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
</nav>

<div class="container" style="max-width:480px;">
    <div class="card">
        <h2>Add User</h2>

        <% String errorMsg = (String) request.getAttribute("errorMsg"); %>
        <% if (errorMsg != null) { %><div class="alert alert-danger"><%= errorMsg %></div><% } %>

        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="add_user"/>

            <div class="form-group">
                <label>Login</label>
                <input type="text" name="login" minlength="3" maxlength="30" required/>
                <span class="form-hint">Minimum 3 characters.</span>
            </div>
            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" minlength="4" maxlength="50" required/>
                <span class="form-hint">Minimum 4 characters.</span>
            </div>
            <div class="form-group">
                <label>Role</label>
                <select name="role">
                    <option value="USER">User</option>
                    <option value="ADMIN">Admin</option>
                </select>
            </div>

            <div class="actions mt-16">
                <button type="submit" class="btn btn-success">Create</button>
                <a href="${pageContext.request.contextPath}/controller?command=show_all_users" class="btn btn-primary">Cancel</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
