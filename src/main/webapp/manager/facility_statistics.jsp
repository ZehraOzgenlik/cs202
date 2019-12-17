<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Facility Statistics</title>

    <jsp:include page="../common/bootstrap.jsp"/>

    <style>
        #facilities {
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
        <th scope="col">Department</th>
        <th scope="col">Usage Count</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="entry" items="${appointmentCounts}" varStatus="stat">
        <tr>
            <th scope="row"><c:out value="${stat.index + 1}"/></th>
            <td><c:out value="${entry.key.name}"/></td>
            <td><c:out value="${entry.value}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
