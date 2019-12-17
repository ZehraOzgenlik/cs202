<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Doctors</title>

    <jsp:include page="../common/bootstrap.jsp"/>

    <style>
        #doctors {
            color: #ffffff;
        }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th scope="col">#</th>
        <th scope="col">ID</th>
        <th scope="col">First Name</th>
        <th scope="col">Last Name</th>
        <th scope="col">Department</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="doctor" items="${doctors}" varStatus="stat">
        <tr>
            <th scope="row"><c:out value="${stat.index + 1}"/></th>
            <td><c:out value="${doctor.id}"/></td>
            <td><c:out value="${doctor.firstName}"/></td>
            <td><c:out value="${doctor.lastName}"/></td>
            <td><c:out value="${doctor.department.name}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
