<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ozyegin.cs202.model.User" %>
<%@ page import="tr.edu.ozyegin.cs202.model.Appointment" %><%--
  Created by IntelliJ IDEA.
  User: Uveys AKBAS
  Date: 12/15/2019
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<%User user = (User) session.getAttribute("currentUser"); %>
<%List<Appointment> appointmentList = (List<Appointment>) request.getAttribute("appointmentList");%>
<%int timeLineCode = (int) session.getAttribute("time_line_code");%> <%-- timeLineCode indicates the past or future appointments (1 == past), (2 == future)--%>
<%! int PAST_APPOINTMENTS_NO = 1;%>
<%! int FUTURE_APPOINTMENTS_NO = 2;%>
<head>
        <title> Welcome <%=user.getFirstName()%>
        </title>
</head>
<body>
<div>
    <form action="appointments" method="post">
        <table border="1">
            <thead>
            <tr>
                <th>Selection</th>
                <th>#</th>
                <th>Doctor Name</th>
                <th>Doctor Surname</th>
                <th>Room</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Department</th>
            </tr>
            </thead>

            <tbody>
            <% if (appointmentList != null && appointmentList.size() != 0) {%>
            <% for (int i = 0; i < appointmentList.size(); i++) {%>
            <% Appointment appointment = appointmentList.get(i);%>
            <tr>
                <% if (timeLineCode == FUTURE_APPOINTMENTS_NO) { %>
                <%--todo There will be a class to control startTime > 24 to show checkbox and allow user to select and cancel --%>
                <td><input type="checkbox" name="selectedDoctors" value=<%=appointment.getId()%>>
                </td>
                <% } else {%>
                <td>
                    *
                </td>
                <% }%>
                <td><%= i + 1 %>
                </td>
                <td><%=appointment.getDoctor().getFirstName()%>
                </td>
                <td><%=appointment.getDoctor().getLastName()%>
                </td>
                <td><%=appointment.getRoom().getName()%>
                </td>
                <td><%=appointment.getStartDate().toString()%>
                </td>
                <td><%=appointment.getEndDate().toString()%>
                </td>
                <td><%=appointment.getDoctor().getDepartment().getName()%>
                </td>

            </tr>


            <%}%>
            <%}%>
            <%--todo  else print "nothing found"  --%>

            </tbody>
        </table>
        <% if (timeLineCode == FUTURE_APPOINTMENTS_NO) { %>  <%-- dont show cancel button if appointments are past --%>
        <input type="submit" name="cancel_appointments_button" value="Cancel Appointments">
        <% } %>
    </form>

</div>

<%-- Dropdown Menu for filter according to Expertise and time--%>
<div>
    <form action="appointments" method="post">

        <label>
            <select name="departments">
                <option value="all_departments">All Departments</option>
                <option value="Cardiology">Cardiology</option>
                <option value="Dermatology">Dermatology</option>
                <option value="General_surgery">General Surgery</option>
                <option value="Neurology">Neurology</option>
                <option value="Radiology">Radiology</option>
            </select>
        </label>


        <label>
            <input type="date" name="date_picker_start">
            <input type="date" name="date_picker_end">
        </label>

        <input type="submit" name="filter_appointments_button" value="Filter Appointments">
    </form>
</div>

</body>

</html>