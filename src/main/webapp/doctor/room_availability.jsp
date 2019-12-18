<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Rooms</title>

    <script type='text/javascript'>
        const calendarProperties = {
            readonly: true,
            eventData: {
                events: [
                    <c:forEach var="reservedTime" items="${reservedTimes}" varStatus="stat">
                    {
                        "id": <c:out value="${reservedTime.id}"/>,
                        "start": new Date(<c:out value="${reservedTime.startYear}"/>, <c:out value="${reservedTime.startMonth}"/>, <c:out value="${reservedTime.startDay}"/>, <c:out value="${reservedTime.startHour}"/>, <c:out value="${reservedTime.startMinute}"/>),
                        "end": new Date(<c:out value="${reservedTime.endYear}"/>, <c:out value="${reservedTime.endMonth}"/>, <c:out value="${reservedTime.endDay}"/>, <c:out value="${reservedTime.endHour}"/>, <c:out value="${reservedTime.endMinute}"/>),
                        "title": '<c:out value="${reservedTime.doctor.department.name}"/>'
                    },
                    </c:forEach>
                ]
            }
        };

        $(document).ready(function () {
            document.getElementById("wc-nav-left").appendChild(document.getElementById("roomNameSelector"));
        });

    </script>

    <jsp:include page="../calendar/calendar.jsp"/>

    <style>
        #rooms {
            color: #ffffff;
        }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div id="roomNameSelector" class="dropdown" style="margin: 20px">
    <form action="../nurse/room_availability" method="get">
        <select name="roomName" onchange="this.form.submit()">
            <option
                    <c:if test="${empty selectedRoom}">selected</c:if>
                    value=null class="dropdown-item">All Rooms
            </option>
            <c:forEach var="room" items="${rooms}">
                <option value='<c:out value="${room.name}"/>'
                        <c:if test="${selectedRoom == room.name}">selected</c:if>
                        class="dropdown-item"><c:out value="${room.name}"/>
                </option>
            </c:forEach>
        </select>
    </form>
</div>

<div id='calendar'></div>

</body>
</html>
