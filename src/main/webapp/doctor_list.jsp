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
    <h1>Doctor List</h1>
    <table border="1">
        <thead>
        <tr>
            <th>#</th>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Department</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="doctor" items="${doctors}" varStatus="stat">
            <tr>
                <td><c:out value="${stat.index + 1}"/></td>
                <td><c:out value="${doctor.id}"/></td>
                <td><c:out value="${doctor.firstName}"/></td>
                <td><c:out value="${doctor.lastName}"/></td>
                <td><c:out value="${doctor.department.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</center>
</body>
</html>
