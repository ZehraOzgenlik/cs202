<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Patients</title>

    <jsp:include page="../common/bootstrap.jsp"/>

    <style>
        #patients {
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
    </tr>
    </thead>
    <tbody>
    <c:forEach var="patient" items="${patients}" varStatus="stat">
        <tr>
            <th scope="row"><c:out value="${stat.index + 1}"/></th>
            <td><c:out value="${patient.id}"/></td>
            <td><c:out value="${patient.firstName}"/></td>
            <td><c:out value="${patient.lastName}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
