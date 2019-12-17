<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Login Page</title>

    <jsp:include page="common/bootstrap.jsp"/>
</head>
<body>
<div class="wrapper">
    <form class="form-signin">
        <h2 class="form-signin-heading">Please login</h2>
        <label>
            <input type="text" class="form-control"
                   id="userId" name="userId" placeholder="User ID" required="" autofocus="" maxlength="11"/>
        </label>
        <label>
            <input type="password" class="form-control"
                   id="password" name="password" placeholder="Password" required=""/>
        </label>
        <div class="form-group mb-2">
            <button type="submit" formaction="login" formmethod="post" class="btn btn-primary">Login</button>
            <button type="submit" formaction="register" formmethod="get" class="btn btn-light">Register</button>
        </div>
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <c:out value="${error}"/>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>
