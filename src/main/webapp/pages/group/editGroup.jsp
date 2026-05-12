<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.kick_4.dao.impl.GroupDaoImpl, com.example.kick_4.entity.Group, java.util.List" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:if test="${empty sessionScope.user_name}">
    <c:redirect url="../auth/login.jsp"/>
</c:if>
<%
    String groupIdParam = request.getParameter("groupId");
    Group group = null;
    if (groupIdParam != null && !groupIdParam.isBlank()) {
        try {
            long id = Long.parseLong(groupIdParam);
            List<Group> all = GroupDaoImpl.getInstance().findAll();
            for (Group g : all) {
                if (g.getId() == id) {
                    group = g;
                    break;
                }
            }
        } catch (Exception e) {}
    }
    if (group == null && request.getAttribute("javax.servlet.forward.request_uri") == null) {
        response.sendRedirect(request.getContextPath() + "/controller?command=SHOW_ALL_GROUPS");
        return;
    }
    request.setAttribute("editGroup", group);
%>
<html>
<head>
    <title>Edit group</title>
</head>
<body>
<h2>Edit group</h2>
<p style="color:red;">${errorMsg}</p>
<c:if test="${not empty editGroup}">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="CHANGE_GROUP"/>
        <input type="hidden" name="groupId" value="${editGroup.id}"/>
        <label>Name: <input type="text" name="groupName" value="${editGroup.name}" required/></label><br/>
        <input type="submit" value="Update group"/>
    </form>
</c:if>
<c:if test="${empty editGroup}">
    <p>Group not found.</p>
</c:if>
<p><a href="${pageContext.request.contextPath}/controller?command=SHOW_ALL_GROUPS">Back to groups</a></p>
</body>
</html>