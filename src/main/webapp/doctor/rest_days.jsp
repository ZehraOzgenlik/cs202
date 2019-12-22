<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Rest Days</title>

    <script type='text/javascript'>
        const calendarProperties = {
            readonly: false,
            eventData: {
                events: [
                    <c:forEach var="restDay" items="${restDays}" varStatus="stat">
                    {
                        "id": <c:out value="${restDay.id}"/>,
                        "start": new Date(<c:out value="${restDay.startYear}"/>, <c:out value="${restDay.startMonth}"/>, <c:out value="${restDay.startDay}"/>, <c:out value="${restDay.startHour}"/>, <c:out value="${restDay.startMinute}"/>),
                        "end": new Date(<c:out value="${restDay.endYear}"/>, <c:out value="${restDay.endMonth}"/>, <c:out value="${restDay.endDay}"/>, <c:out value="${restDay.endHour}"/>, <c:out value="${restDay.endMinute}"/>),
                        "title": 'Rest Day #<c:out value="${restDay.id}"/>'
                    },
                    </c:forEach>
                ],
            },
            eventClick: function (calEvent, $event) {
                eventClick(calEvent);
            }
        };

        function eventClick(calEvent) {
            const $dialogContent = $("#event_edit_container");
            const titleField = $dialogContent.find("label[id='eventTitle']");
            const eventIdField = $dialogContent.find("input[name='eventId']");
            const startField = $dialogContent.find("input[name='startTime']");
            const endField = $dialogContent.find("input[name='endTime']");

            titleField.text(calEvent.title);
            eventIdField.val(calEvent.id);
            startField.val(calEvent.start.toIsoString().slice(0,19));
            endField.val(calEvent.end.toIsoString().slice(0,19));

            $dialogContent.show();
        }

        function close() {
            const $dialogContent = $("#event_edit_container");
            $dialogContent.dialog("destroy");
            $dialogContent.close();
        }

        <c:if test="${not empty message}">
        alert('<c:out value="${message}"/>');
        </c:if>

    </script>

    <jsp:include page="../calendar/calendar.jsp"/>

    <style>
        #rest_days {
            color: #ffffff;
        }
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<div id='calendar'></div>
<div id="event_edit_container" tabindex="-1" class="modal" role="dialog">
    <div class="modal-dialog" role="document">
        <form method="post" action="rest_days">
            <div class="modal-content">
                <div class="modal-header">
                    <label id="eventTitle" class="modal-title">New Rest</label>
                    <button type="submit" class="close" data-dismiss="modal"
                            aria-label="Close" onclick="close()" formmethod="get">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input name="eventId" hidden>
                    <input type="datetime-local" class="form-control" id="startTime" name="startTime"
                           value="${requestScope.startTime}" style="margin-bottom: 5px">
                    <input type="datetime-local" class="form-control" id="endTime" name="endTime"
                           value="${requestScope.endTime}" style="margin-bottom: 5px">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" name="update"
                            data-dismiss="modal" onclick="close()">Save
                    </button>
                    <button type="submit" class="btn btn-secondary" name="delete"
                            formaction="" data-dismiss="modal" onclick="close()">Delete
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
