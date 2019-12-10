<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Login Page</title>
</head>
<body>
<h1>Please enter your user id and password to login</h1>
<form action="login" method="post">
    <table>
        <tr>
            <td>User Id</td>
            <td>
                <label>
                    <input name="user_id" type="text"/>
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
            <td><input type="submit" value="Login"/></td>
        <tr>
    </table>
</form>
<%
    String errorMessage = (String) request.getAttribute("error");
    errorMessage = errorMessage == null ? "" : errorMessage;
%>
<span style="font-size: small; color: red; "><%=errorMessage%></span>
</body>
</html>
