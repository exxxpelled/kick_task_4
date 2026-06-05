<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.example.kick_4.entity.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <%@ include file="/pages/common-styles.jspf" %>
</head>
<body>

<nav>
    <span class="brand">Student Manager</span>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_students">Students</a>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_users">Users</a>
    <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
</nav>

<div class="container">
    <div class="card">
        <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px;">
            <h2>Users</h2>
            <a href="${pageContext.request.contextPath}/pages/user/addUser.jsp" class="btn btn-success">Add User</a>
        </div>

        <%
            String successMsg = (String) request.getAttribute("successMsg");
            String errorMsg   = (String) request.getAttribute("errorMsg");
        %>
        <% if (successMsg != null) { %><div class="alert alert-success"><%= successMsg %></div><% } %>
        <% if (errorMsg   != null) { %><div class="alert alert-danger"><%= errorMsg %></div><% } %>

        <%
            List<User> users = (List<User>) request.getAttribute("users");
        %>
        <% if (users == null || users.isEmpty()) { %>
        <p class="text-muted" style="padding:24px 0;">No users found.</p>
        <% } else { %>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Login</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (User u : users) { %>
            <tr>
                <td><%= u.getId() %></td>
                <td><%= u.getLogin() %></td>
                <td><%= u.getRole().name() %></td>
                <td>
                    <div class="actions">
                        <a href="${pageContext.request.contextPath}/pages/user/editUser.jsp?userId=<%= u.getId() %>&login=<%= u.getLogin() %>&role=<%= u.getRole().name() %>"
                           class="btn btn-warning">Edit</a>
                        <form action="${pageContext.request.contextPath}/controller" method="post"
                              onsubmit="return confirm('Delete user <%= u.getLogin() %>?')">
                            <input type="hidden" name="command" value="delete_user"/>
                            <input type="hidden" name="userId" value="<%= u.getId() %>"/>
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form>
                    </div>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</div>

</body>
</html>
