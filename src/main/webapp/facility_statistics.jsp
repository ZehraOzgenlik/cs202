<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home Page</title>
</head>
<body>
<center>
    <h1>Facility Usage Counts By Departments</h1>
    <table border="1">
        <thead>
        <tr>
            <th>Department</th>
            <th>Usage Count</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="entry" items="${appointmentCounts}">
            <tr>
                <td><c:out value="${entry.key.name}"/></td>
                <td><c:out value="${entry.value}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</center>
</body>
</html>
