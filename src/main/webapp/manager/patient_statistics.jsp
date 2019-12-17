<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Patient Statistics</title>

    <jsp:include page="../common/bootstrap.jsp"/>

    <style>
        #statistics {
            color: #ffffff;
        }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">#</th>
        <th scope="col">First Name</th>
        <th scope="col">Last Name</th>
        <th scope="col">Department</th>
        <th scope="col">Treatment Type</th>
        <th scope="col">Room</th>
        <th scope="col">Start Time</th>
        <th scope="col">End Time</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="appointment" items="${appointments}" varStatus="stat">
        <tr>
            <th scope="row"><c:out value="${stat.index + 1}"/></th>
            <td><c:out value="${appointment.doctor.department.name}"/></td>
            <td><c:out value="${appointment.doctor.firstName}"/></td>
            <td><c:out value="${appointment.doctor.lastName}"/></td>
            <td><c:out value="${appointment.treatmentType.name}"/></td>
            <td><c:out value="${appointment.room.name}"/></td>
            <td><fmt:formatDate type="date" pattern="dd-MM-yyyy HH:mm" value="${appointment.startDate}"/></td>
            <td><fmt:formatDate type="date" pattern="dd-MM-yyyy HH:mm" value="${appointment.endDate}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<hr style="border-top: 1px solid grey;">
<form>
    <div class="form-row" style="margin-left: 30px;">
        <div class="form-group col-md-2">
            <label for="startTime">Start Time</label>
            <input type="date" class="form-control" id="startTime" name="startTime"
                   value="${requestScope.startTime}">
        </div>
        <div class="form-group col-md-2">
            <label for="endTime">End Time</label>
            <input type="date" class="form-control" id="endTime" name="endTime" value="${requestScope.endTime}">
        </div>
        <div class="form-group  col-md-3" style="margin-top: 35px">
            <button class="btn btn-primary" formaction="patient_statistics" formmethod="get">Fetch</button>
        </div>
    </div>
</form>
</body>
</html>
