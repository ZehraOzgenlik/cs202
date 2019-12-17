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

    <h2>
        <c:out value="${sessionScope.currentUser.fullName} => ${sessionScope.currentUser.userType.name}"/>
    </h2>

    <form action="patients" method="get">
        <input type="submit" name="past_appointments" value="Past Appointments">
        <input type="submit" name="make_new_appointment" value="Make New Appointment">
        <input type="submit" name="future_appointments" value="Future Appointment">
    </form>

    <form>
        <input type="submit" value="Logout" formaction="../logout" formmethod="get"/>
    </form>
</div>
</body>
</html>
