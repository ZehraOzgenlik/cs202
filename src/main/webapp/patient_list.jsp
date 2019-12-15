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
    <h1>Patient List</h1>
    <table border="1">
        <thead>
        <tr>
            <th>#</th>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" begin="1" end="${patients.size()}">
            <c:set var="patient" value="${patients.get(i-1)}"/>
            <tr>
                <td><c:out value="${i}"/></td>
                <td><c:out value="${patient.id}"/></td>
                <td><c:out value="${patient.firstName}"/></td>
                <td><c:out value="${patient.lastName}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</center>
</body>
</html>
