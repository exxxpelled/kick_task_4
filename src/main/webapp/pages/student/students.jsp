<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, com.example.kick_4.entity.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Students</title>
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
            <h2>Students</h2>
            <a href="${pageContext.request.contextPath}/pages/student/addStudent.jsp" class="btn btn-success">Add Student</a>
        </div>

        <%
            String successMsg = (String) request.getAttribute("successMsg");
            String errorMsg   = (String) request.getAttribute("errorMsg");
        %>
        <% if (successMsg != null) { %><div class="alert alert-success"><%= successMsg %></div><% } %>
        <% if (errorMsg   != null) { %><div class="alert alert-danger"><%= errorMsg %></div><% } %>

        <%
            List<Student> students = (List<Student>) request.getAttribute("students");
        %>
        <% if (students == null || students.isEmpty()) { %>
        <p class="text-muted" style="padding:24px 0;">No students found.</p>
        <% } else { %>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Group</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <% for (Student s : students) { %>
            <tr>
                <td><%= s.getId() %></td>
                <td><%= s.getName() %></td>
                <td><%= s.getSurname() %></td>
                <td><%= s.getGroupNumber() %></td>
                <td>
                    <div class="actions">
                        <a href="${pageContext.request.contextPath}/pages/student/editStudent.jsp?studentId=<%= s.getId() %>&name=<%= s.getName() %>&surname=<%= s.getSurname() %>&groupId=<%= s.getGroupNumber() %>"
                           class="btn btn-warning">Edit</a>
                        <form action="${pageContext.request.contextPath}/controller" method="post"
                              onsubmit="return confirm('Delete this student?')">
                            <input type="hidden" name="command" value="delete_student"/>
                            <input type="hidden" name="studentId" value="<%= s.getId() %>"/>
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
