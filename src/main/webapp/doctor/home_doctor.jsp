<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${sessionScope.currentUser.userType.name} Home Page</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Welcome to hospital management system</h1>

    <h2>
        <c:out value="${sessionScope.currentUser.fullName}"/>
    </h2>

    <form action="doctors" method="get" style="background-color: cadetblue">
        <input type="submit" name="doctor_appointments_past_button" value="Show Past Appointments">
        <input type="submit" name="doctor_appointments_future_button" value="Show Future Appointments">
        <input type="submit" name="rest_days_button" value="Show Rest Days">
        <input type="submit" name="available_rooms_button" value="Show Available Rooms">
    </form>

    <form>
        <input type="submit" value="Logout" formaction="../logout" formmethod="get"/>
    </form>
</div>
</body>
</html>
