<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit User</title>
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
        <h2>Edit User</h2>

        <% String errorMsg = (String) request.getAttribute("errorMsg"); %>
        <% if (errorMsg != null) { %><div class="alert alert-danger"><%= errorMsg %></div><% } %>

        <%
            String userId   = request.getParameter("userId");
            String login    = request.getParameter("login");
            String userRole = request.getParameter("role");
            if (userRole == null) userRole = "USER";
        %>

        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="change_user"/>
            <input type="hidden" name="userId" value="<%= userId %>"/>

            <div class="form-group">
                <label>Login</label>
                <input type="text" name="login"
                       value="<%= login != null ? login : "" %>"
                       minlength="3" maxlength="30" required/>
            </div>
            <div class="form-group">
                <label>New Password</label>
                <input type="password" name="password" minlength="4" maxlength="50"/>
                <span class="form-hint">Leave blank to keep the current password.</span>
            </div>
            <div class="form-group">
                <label>Role</label>
                <select name="role">
                    <option value="USER"  <%= "USER".equals(userRole)  ? "selected" : "" %>>User</option>
                    <option value="ADMIN" <%= "ADMIN".equals(userRole) ? "selected" : "" %>>Admin</option>
                </select>
            </div>

            <div class="actions mt-16">
                <button type="submit" class="btn btn-warning">Update</button>
                <a href="${pageContext.request.contextPath}/controller?command=show_all_users" class="btn btn-primary">Cancel</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
