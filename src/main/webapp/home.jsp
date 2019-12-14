<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="tr.edu.ozyegin.cs202.service.model.User" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home Page</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Welcome to hospital management system</h1>

    <%
        User user = (User) session.getAttribute("user");
    %>
    <h2><%=user.firstName%> <%=user.lastName%> => <%=user.userType.name%>
    </h2>

    <form action="doctor_list" method="get">
        <input type="submit" value="Show Doctors"/>
    </form>

    <form action="patient_list" method="get">
        <input type="submit" value="Show Patients"/>
    </form>
</div>
</body>
</html>
