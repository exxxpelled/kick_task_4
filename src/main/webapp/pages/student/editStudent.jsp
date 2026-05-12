<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.kick_4.dao.impl.StudentDaoImpl, com.example.kick_4.entity.Student, java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<%
    String studentIdParam = request.getParameter("studentId");
    Student student = null;
    if (studentIdParam != null && !studentIdParam.isBlank()) {
        try {
            long id = Long.parseLong(studentIdParam);
            List<Student> all = StudentDaoImpl.getInstance().findAll();
            for (Student s : all) {
                if (s.getId() == id) {
                    student = s;
                    break;
                }
            }
        } catch (Exception e) {}
    }
    if (student == null && request.getAttribute("javax.servlet.forward.request_uri") == null) {
        response.sendRedirect(request.getContextPath() + "/controller?command=SHOW_ALL_STUDENTS");
        return;
    }
    request.setAttribute("editStudent", student);
%>
<html>
<head>
    <title>Edit student</title>
</head>
<body>
<h2>Edit student</h2>
<p style="color:red;">${errorMsg}</p>
<c:if test="${not empty editStudent}">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="CHANGE_STUDENT"/>
        <input type="hidden" name="studentId" value="${editStudent.id}"/>
        <label>Name: <input type="text" name="name" value="${editStudent.name}" required/></label><br/>
        <label>Surname: <input type="text" name="surname" value="${editStudent.surname}" required/></label><br/>
        <label>Group ID: <input type="number" name="groupId" value="${editStudent.groupId}" required/></label><br/>
        <input type="submit" value="Update student"/>
    </form>
</c:if>
<c:if test="${empty editStudent}">
    <p>Student not found.</p>
</c:if>
<p><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_STUDENTS">Back to students</a></p>
</body>
</html>