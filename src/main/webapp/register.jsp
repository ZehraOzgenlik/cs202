<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Registration Page</title>
</head>
<body>
<h1>Please fill below fields to register</h1>
<form action="register" method="post">
    <table>
        <tr>
            <td>User Id</td>
            <td>
                <label>
                    <input name="user_id" type="text" maxlength="11"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>First Name</td>
            <td>
                <label>
                    <input name="first_name" type="text"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td>
                <label>
                    <input name="last_name" type="text"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>Password</td>
            <td>
                <label>
                    <input name="password" type="password"/>
                </label>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Register"/></td>
        <tr>
    </table>
</form>
<c:if test="${not empty error}">
    <span style="font-size: small; color: red; ">${error}</span>
</c:if>
</body>
</html>
