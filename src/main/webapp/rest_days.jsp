<%@ page import="tr.edu.ozyegin.cs202.model.User" %>
<%@ page import="tr.edu.ozyegin.cs202.model.RestDay" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ozyegin.cs202.service.resday.RestDayService" %><%--
  Created by IntelliJ IDEA.
  User: Uveys AKBAS
  Date: 12/17/2019
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<html>

<%User user = (User) session.getAttribute("currentUser"); %>
<%List<RestDay> restDayList = (List<RestDay>) request.getAttribute("restDayList");%>
<%RestDayService restDayService = new RestDayService();%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Rest Days</title>
</head>
<body>
<center>

    <div>
        <form action="restDays" method="post">
            <table border="1">
                <thead>
                <tr>
                    <th>Selection</th>
                    <th>#</th>
                    <th>ID</th>
                    <th>Start Time</th>
                    <th>End Time</th>
                </tr>
                </thead>

                <tbody>
                <% if (restDayList != null && restDayList.size() != 0) {%>
                <% for (int i = 0; i < restDayList.size(); i++) {%>
                <% RestDay restDay = restDayList.get(i);%>
                <tr>
                    <% if (restDayService.isDayPassed(restDay)) { %>
                    <td>
                        *
                    </td>
                    <% } else {%>
                    <td><input type="checkbox" name="selectedRestDays" value=<%=restDay.getId()%>>
                    </td>
                    <% }%>
                    <td><%= i + 1 %>
                    </td>
                    <td><%=restDay.getId()%>
                    </td>
                    <td><%=restDay.getStartDate().toString()%>
                    </td>
                    <td><%=restDay.getEndDate().toString()%>
                    </td>
                </tr>

                <%}%>
                <%}%>
                </tbody>
            </table>

            <input type="submit" name="cancel_rest_day_button" value="Cancel Rest Day">

        </form>
    </div>

    <div style="background-color: darkgreen">
        <form action="restDays" method="post">
            <input type="submit" name="add_new_rest_day_button" value="Add New Rest Day">
        </form>
    </div>

    <form>
        <div style="background-color: red">
            <input type="submit" value="Logout" formaction="logout" formmethod="get"/>
        </div>
    </form>

</center>
</body>
</html>
