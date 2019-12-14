<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="tr.edu.ozyegin.cs202.service.model.Department" %>
<%@ page import="tr.edu.ozyegin.cs202.service.model.Doctor" %>
<%@ page import="java.util.List" %>
<%@ page import="tr.edu.ozyegin.cs202.service.model.Patient" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home Page</title>
</head>
<body>
<center>
    <h1>Patient List</h1>
    <table border="1">
        <thead>
        <tr>
            <th>#</th>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Patient> patients = (List<Patient>) request.getAttribute("patients");
            for (int i = 0; i < patients.size(); i++) {
                Patient patient = patients.get(i);
        %>
        <tr>
            <td><%=i + 1%>
            </td>
            <td><%=patient.id%>
            </td>
            <td><%=patient.firstName%>
            </td>
            <td><%=patient.lastName%>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</center>
</body>
</html>
