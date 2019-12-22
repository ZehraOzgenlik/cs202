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
            readonly: false,
            eventData: {
                events: [
                    <c:forEach var="reservedTime" items="${reservedTimes}" varStatus="stat">
                    {
                        "id": <c:out value="${reservedTime.id}"/>,
                        "start": new Date(<c:out value="${reservedTime.startYear}"/>, <c:out value="${reservedTime.startMonth}"/>, <c:out value="${reservedTime.startDay}"/>, <c:out value="${reservedTime.startHour}"/>, <c:out value="${reservedTime.startMinute}"/>),
                        "end": new Date(<c:out value="${reservedTime.endYear}"/>, <c:out value="${reservedTime.endMonth}"/>, <c:out value="${reservedTime.endDay}"/>, <c:out value="${reservedTime.endHour}"/>, <c:out value="${reservedTime.endMinute}"/>),
                        "title": '<c:out value="${reservedTime.doctor.department.name}"/>',
                        "body": '<c:out value="${reservedTime.patient.firstName} ${reservedTime.patient.lastName}"/>',
                    },
                    </c:forEach>
                ]
            },
            eventClick: function (calEvent, $event) {
                eventClick(calEvent);
            }
        };

        function eventClick(calEvent) {
            const $calendar = $('#calendar');
            const $dialogContent = $("#event_view_container");
            const titleField = $dialogContent.find("label[id='eventTitle']");
            const bodyField = $dialogContent.find("label[id='eventBody']");
            const startField = $dialogContent.find("label[id='startTime']");
            const endField = $dialogContent.find("label[id='endTime']");

            titleField.text(calEvent.title);
            bodyField.text(calEvent.body);
            startField.text($calendar.weekCalendar("formatDate", calEvent.start, "Y-m-d H:i"));
            endField.text($calendar.weekCalendar("formatDate", calEvent.end, "H:i"));

            $dialogContent.show();
        }

        function close() {
            const $dialogContent = $("#event_view_container");
            $dialogContent.dialog("destroy");
            $dialogContent.close();
        }

        window.onload = function () {
            document.getElementById("wc-nav-left").appendChild(document.getElementById("roomNameSelector"));
        };

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

<div id="event_view_container" tabindex="-1" class="modal" role="dialog">
    <div class="modal-dialog" role="document">
        <form>
            <div class="modal-content">
                <div class="modal-header">
                    <label id="eventTitle" class="modal-title">Modal title</label>
                    <button type="submit" class="close" data-dismiss="modal" aria-label="Close" onclick="close()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <label id="eventBody" style="margin-bottom: 5px"></label>
                    <br/>
                    <label id="startTime" style="margin-bottom: 5px"></label>
                    <b>-</b>
                    <label id="endTime" style="margin-bottom: 5px"></label>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
</html>
