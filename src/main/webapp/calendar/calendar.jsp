<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js' type='text/javascript'></script>

<script type='text/javascript'>
    <jsp:include page="../calendar/libs/jquery-ui-1.8rc3.custom.min.js"/>
    <jsp:include page="../calendar/jquery.weekcalendar.js"/>

    $(document).ready(function () {

        $('#calendar').weekCalendar({
            use24Hour: true,
            timeFormat: "H:i",
            firstDayOfWeek: 1,
            readonly: calendarProperties.readonly,
            height: function ($calendar) {
                return $(window).height() - $("h1").outerHeight();
            },
            eventNew: function (calEvent, $event) {
                calendarProperties.eventNew(calEvent, $event)
            },
            eventClick: function (calEvent, $event) {
                calendarProperties.eventClick(calEvent, $event)
            },
            eventRender: function (calEvent, $event) {
                if (calEvent.end.getTime() < new Date().getTime()) {
                    $event.css("backgroundColor", "#aaa");
                    $event.find(".time").css({"backgroundColor": "#999", "border": "1px solid #888"});
                }
            },
            noEvents: function () {
                displayMessage("There are no events for this week");
            },
            data: calendarProperties.eventData
        });

        function displayMessage(message) {
            $("#message").html(message).fadeIn();
        }

        $("<div id=\"message\" class=\"ui-corner-all\"></div>").prependTo($("body"));
        document.getElementById("wc-nav-left").appendChild(document.getElementById("roomNameSelector"));
    });
</script>

<style>
    <jsp:include page="../calendar/libs/css/smoothness/jquery-ui-1.8rc3.custom.css"/>
    <jsp:include page="../calendar/jquery.weekcalendar.css"/>
    <jsp:include page="../common/custom.css"/>
</style>

<!-- Bootstrap -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
      integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">