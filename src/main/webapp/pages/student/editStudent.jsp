<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Student</title>
    <%@ include file="/pages/common-styles.jspf" %>
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
        <h2>Edit Student</h2>

        <% String errorMsg = (String) request.getAttribute("errorMsg"); %>
        <% if (errorMsg != null) { %><div class="alert alert-danger"><%= errorMsg %></div><% } %>

        <%
            String studentId = request.getParameter("studentId");
            String name      = request.getParameter("name");
            String surname   = request.getParameter("surname");
            String groupId   = request.getParameter("groupId");
        %>

        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="change_student"/>
            <input type="hidden" name="studentId" value="<%= studentId %>"/>

            <div class="form-group">
                <label>First Name</label>
                <input type="text" name="name" value="<%= name != null ? name : "" %>" required/>
            </div>
            <div class="form-group">
                <label>Surname</label>
                <input type="text" name="surname" value="<%= surname != null ? surname : "" %>" required/>
            </div>
            <div class="form-group">
                <label>Group Number</label>
                <input type="number" name="groupId" value="<%= groupId != null ? groupId : "" %>" min="1" required/>
            </div>

            <div class="actions mt-16">
                <button type="submit" class="btn btn-warning">Update</button>
                <a href="${pageContext.request.contextPath}/controller?command=show_all_students" class="btn btn-primary">Cancel</a>
            </div>
        </form>
    </div>
</div>

</body>
</html>
