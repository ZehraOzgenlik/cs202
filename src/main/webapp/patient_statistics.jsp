<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home Page</title>
</head>
<body>
<center>
    <h1>Patient List on a Time Period</h1>
    <form>
        <table>
            <tr>
                <td>Start Time</td>
                <td>
                    <label>
                        <input name="start_time" type="date" value="${requestScope.start_time}"/>
                    </label>
                </td>
                <td>End Time</td>
                <td>
                    <label>
                        <input name="end_time" type="date" value="${requestScope.end_time}"/>
                    </label>
                </td>
                <td><input type="submit" value="Fetch" formaction="patient_statistics" formmethod="get"/></td>
            <tr>
        </table>
    </form>

    <h2><c:out value="Total patient count: ${appointments.size()}"/></h2>
    <table border="1">
        <thead>
        <tr>
            <th>Appointment</th>
            <th colspan="2">Patient</th>
            <th colspan="3">Doctor</th>
            <th colspan="2">Treatment</th>
            <th colspan="2">Time Period</th>
        </tr>
        <tr>
            <th>Appointment ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Department</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Treatment Type</th>
            <th>Room</th>
            <th>Start Time</th>
            <th>End Time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="appointment" items="${appointments}" varStatus="stat">
            <tr>
                <td><c:out value="${appointment.id}"/></td>
                <td><c:out value="${appointment.patient.firstName}"/></td>
                <td><c:out value="${appointment.patient.lastName}"/></td>
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
</center>
</body>
</html>
