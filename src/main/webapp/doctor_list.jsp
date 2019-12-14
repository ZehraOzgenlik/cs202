<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="tr.edu.ozyegin.cs202.service.model.Department" %>
<%@ page import="tr.edu.ozyegin.cs202.service.model.Doctor" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home Page</title>
</head>
<body>
<center>
    <h1>Doctors List</h1>
    <table border="1">
        <thead>
        <tr>
            <th>#</th>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Department</th>
        </tr>
        </thead>
        <tbody>
        <%
            List<Doctor> doctors = (List<Doctor>) request.getAttribute("doctors");
            for (int i = 0; i < doctors.size(); i++) {
                Doctor doctor = doctors.get(i);
                String departments = "";
                for (int j = 0; j < doctor.departments.size(); j++) {
                    Department department = doctor.departments.get(j);
                    departments += department.name;
                    if (j < doctor.departments.size() - 1) {
                        departments += ", ";
                    }
                }
        %>
        <tr>
            <td><%=i + 1%>
            </td>
            <td><%=doctor.id%>
            </td>
            <td><%=doctor.firstName%>
            </td>
            <td><%=doctor.lastName%>
            </td>
            <td><%=departments%>
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
