<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>New Appointment</title>

    <script type='text/javascript'>
        <c:if test="${not empty message}">
        alert('<c:out value="${message}"/>');
        </c:if>
    </script>

    <jsp:include page="../common/bootstrap.jsp"/>

    <style>
        #new_appointment {
            color: #ffffff;
        }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<form>
    <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Selection</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Department</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="doctor" items="${doctors}" varStatus="stat">
            <tr>
                <th scope="row"><c:out value="${stat.index + 1}"/></th>
                <td><input type="radio" name="doctorID" value='<c:out value="${doctor.id}"/>'></td>
                <td><c:out value="${doctor.firstName}"/></td>
                <td><c:out value="${doctor.lastName}"/></td>
                <td><c:out value="${doctor.department.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <hr style="border-top: 1px solid grey;">

    <div class="form-row" style="margin-left: 30px;">

        <div class="form-group col-md-3">
            <label for="department">Department</label>
            <select name="department" id="department">
                <option
                        <c:if test="${empty selectedDepartment}">selected</c:if>
                        value=null class="dropdown-item">All Departments
                </option>
                <c:forEach var="department" items="${departments}">
                    <option value='<c:out value="${department.id}"/>'
                            <c:if test="${selectedDepartment == department.id}">selected</c:if>
                            class="dropdown-item"><c:out value="${department.name}"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <%--Todo        These 2 <div> shows only date not hour and minute. Hour and minute should be added.--%>
        <div class="form-group col-md-2">
            <label for="startTime">Start Time For Filtering</label>
            <input type="datetime-local" class="form-control" id="startTime" name="startTime"
                   value="${requestScope.startTime}">
        </div>

        <div class="form-group col-md-2">
            <label for="endTime">End Time For Filtering</label>
            <input type="datetime-local" class="form-control" id="endTime" name="endTime"
                   value="${requestScope.endTime}">
        </div>

        <div class="form-group col-md-3" style="margin-top: 35px">
            <button class="btn btn-primary" formaction="new_appointment" formmethod="get">Filter</button>
        </div>

    </div>

    <div class="form-row" style="margin-left: 500px;">
        <div class="form-group col-md-4">
            <label for="startTime">Appointment Start Time</label>
            <input type="datetime-local" class="form-control" id="appointmentStartTime" name="appointmentStartTime"
                   value="${requestScope.startTime}">
        </div>

        <div class="form-group col-md-3" style="margin-top: 35px">
            <button class="btn btn-secondary" formaction="new_appointment" formmethod="post" name="action"
                    value="saveAppointments">Make Appointment
            </button>
        </div>
    </div>
</form>

</body>
</html>
