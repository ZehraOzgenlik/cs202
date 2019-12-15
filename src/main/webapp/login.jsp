<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Login Page</title>
</head>
<body>
<h1>Please enter your user id and password to login</h1>
<form>
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
            <td>Password</td>
            <td>
                <label>
                    <input name="password" type="password"/>
                </label>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Login" formaction="login" formmethod="post"/></td>
            <td><input type="submit" value="Register" formaction="register" formmethod="get"/></td>
        <tr>
    </table>
</form>
<c:if test="${not empty error}">
    <span style="font-size: small; color: red; ">${error}</span>
</c:if>
</body>
</html>
