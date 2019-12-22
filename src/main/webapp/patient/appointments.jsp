<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Appointments</title>

    <jsp:include page="../common/bootstrap.jsp"/>

    <script type='text/javascript'>
        <c:if test="${not empty error}">
        alert('<c:out value="${error}"/>');
        </c:if>

        function checkSelection(id) {
            const checkBoxes = document.getElementsByClassName('appointment-checkbox');
            for (let i = 1; i <= checkBoxes.length; i++) {
                checkBoxes[i].checked = (checkBoxes[i].id === id);
            }
        }
    </script>

    <style>
        <c:if test='${type == "past"}'>
        #past_appointments {

        </c:if>
        <c:if test='${type == "future"}'>
        #future_appointments {
        </c:if> color: #ffffff;
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
            <th scope="col">Treatment Type</th>
            <th scope="col">Department</th>
            <th scope="col">Room</th>
            <th scope="col">Start Time</th>
            <th scope="col">End Time</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="appointment" items="${appointments}" varStatus="stat">
            <tr>
                <th scope="row"><c:out value="${stat.index + 1}"/></th>
                <td>
                    <input type="checkbox" class="appointment-checkbox" name="appointmentId"
                           onclick="checkSelection(this.id)" id='<c:out value="${appointment.id}"/>'
                           value='<c:out value="${appointment.id}"/>'/>
                </td>
                <td><c:out value="${appointment.patient.firstName}"/></td>
                <td><c:out value="${appointment.patient.lastName}"/></td>
                <td><c:out value="${appointment.treatmentType.name}"/></td>
                <td><c:out value="${appointment.doctor.department.name}"/></td>
                <td><c:out value="${appointment.room.name}"/></td>
                <td><fmt:formatDate type="date" pattern="dd-MM-yyyy HH:mm" value="${appointment.startDate}"/></td>
                <td><fmt:formatDate type="date" pattern="dd-MM-yyyy HH:mm" value="${appointment.endDate}"/></td>
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
        <div class="form-group col-md-2">
            <label for="startTime">Start Time</label>
            <input type="date" class="form-control" id="startTime" name="startTime"
                   value="${requestScope.startTime}">
        </div>
        <div class="form-group col-md-2">
            <label for="endTime">End Time</label>
            <input type="date" class="form-control" id="endTime" name="endTime" value="${requestScope.endTime}">
        </div>
        <div class="form-group col-md-3" style="margin-top: 35px">
            <button class="btn btn-primary" formaction="appointments"
                    formmethod="get">Fetch
            </button>
            <button class="btn btn-danger" formaction="appointments"
                    formmethod="post" name="action" value="cancelAppointments">Cancel
            </button>
        </div>
    </div>
</form>
</body>
</html>
