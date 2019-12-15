<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Home Page</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Welcome to hospital management system</h1>

    <h2><c:out value="${sessionScope.currentUser.firstName} ${sessionScope.currentUser.lastName} => ${sessionScope.currentUser.userType.name}"/></h2>

    <form action="doctor_list" method="get">
        <input type="submit" value="Show Doctors"/>
    </form>

    <form action="patient_list" method="get">
        <input type="submit" value="Show Patients"/>
    </form>
</div>
</body>
</html>
