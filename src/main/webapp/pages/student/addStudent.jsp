<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Student</title>
    <%@ include file="/pages/common-styles.jspf" %>
</head>
<body>

<nav>
    <span class="brand">📚 Student Manager</span>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_students">Students</a>
    <a href="${pageContext.request.contextPath}/controller?command=show_all_users">Users</a>
    <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
</nav>

<div class="container" style="max-width:480px;">
    <div class="card">
        <h2>Add Student</h2>

        <% String errorMsg = (String) request.getAttribute("errorMsg"); %>
        <% if (errorMsg != null) { %><div class="alert alert-danger"><%= errorMsg %></div><% } %>

        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="add_student"/>

            <div class="form-group">
                <label>First Name</label>
                <input type="text" name="name" placeholder="e.g. Ivan" required/>
            </div>
            <div class="form-group">
                <label>Surname</label>
                <input type="text" name="surname" placeholder="e.g. Ivanov" required/>
            </div>
            <div class="form-group">
                <label>Group Number</label>
                <input type="number" name="groupNumber" placeholder="e.g. 101" min="1" required/>
            </div>

            <div style="display:flex; gap:10px; margin-top:8px;">
                <button type="submit" class="btn btn-success">Save</button>
                <a href="${pageContext.request.contextPath}/controller?command=show_all_students" class="btn btn-primary">Cancel</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
