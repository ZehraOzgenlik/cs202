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
            const $calendar = $('#calendar');
            const $dialogContent = $("#event_edit_container");
            const titleField = $dialogContent.find("label[id='eventTitle']");
            const eventDateField = $dialogContent.find("input[name='eventDate']");
            const startField = $dialogContent.find("select[name='startTime']");
            const endField = $dialogContent.find("select[name='endTime']");

            titleField.text(calEvent.title);
            eventDateField.val($calendar.weekCalendar("formatDate", calEvent.start, "Y-m-d"));
            startField.val(calEvent.start);
            endField.val(calEvent.end);

            setupStartAndEndTimeFields(startField, endField, calEvent, $calendar.weekCalendar("getTimeslotTimes", calEvent.start));

            $dialogContent.show();
        }

        function setupStartAndEndTimeFields($startTimeField, $endTimeField, calEvent, timeslotTimes) {
            for (let i = 0; i < timeslotTimes.length; i++) {
                const startTime = timeslotTimes[i].start;
                const endTime = timeslotTimes[i].end;
                let startSelected = "";
                if (startTime.getTime() === calEvent.start.getTime()) {
                    startSelected = "selected=\"selected\"";
                }
                let endSelected = "";
                if (endTime.getTime() === calEvent.end.getTime()) {
                    endSelected = "selected=\"selected\"";
                }
                $startTimeField.append("<option value=\"" + startTime + "\" " + startSelected + ">" + timeslotTimes[i].startFormatted + "</option>");
                $endTimeField.append("<option value=\"" + endTime + "\" " + endSelected + ">" + timeslotTimes[i].endFormatted + "</option>");

            }

            //reduces the end time options to be only after the start time options.
            const $endTimeOptions = $endTimeField.find("option");
            $startTimeField.change(function () {
                const startTime = $(this).find(":selected").val();
                const currentEndTime = $endTimeField.find("option:selected").val();
                $endTimeField.html(
                    $endTimeOptions.filter(function () {
                        return startTime < $(this).val();
                    })
                );

                let endTimeSelected = false;
                $endTimeField.find("option").each(function () {
                    if ($(this).val() === currentEndTime) {
                        $(this).attr("selected", "selected");
                        endTimeSelected = true;
                        return false;
                    }
                });

                if (!endTimeSelected) {
                    //automatically select an end date 2 slots away.
                    $endTimeField.find("option:eq(1)").attr("selected", "selected");
                }
            });
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
                    <label id="eventTitle" class="modal-title">Modal title</label>
                    <button type="submit" class="close" data-dismiss="modal" aria-label="Close" onclick="close()">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <input type="date" class="form-control" id="eventDate" name="eventDate"
                           value="${requestScope.startTime}" required="" style="margin-bottom: 5px">
                    <select name="startTime" required="">
                        <option value="">Select Start Time</option>
                    </select>
                    <select name="endTime" required="">
                        <option value="">Select Start Time</option>
                    </select>
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
</div>
</body>
</html>
