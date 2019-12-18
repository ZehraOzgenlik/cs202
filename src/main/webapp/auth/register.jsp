<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Registration Page</title>

    <jsp:include page="../common/bootstrap.jsp"/>
</head>
<body>
<div class="wrapper">
    <form class="form-signin">
        <h2 class="form-signin-heading">Please Sign Up</h2>
        <label>
            <input type="text" class="form-control"
                   id="userId" name="userId" placeholder="User ID" required="" autofocus="" maxlength="11"/>
        </label>
        <label>
            <input type="text" class="form-control"
                   id="firstName" name="firstName" placeholder="First Name" required=""/>
        </label>
        <label>
            <input type="text" class="form-control"
                   id="lastName" name="lastName" placeholder="Last Name" required=""/>
        </label>
        <label>
            <input type="password" class="form-control"
                   id="password" name="password" placeholder="Password" required=""/>
        </label>
        <div class="form-group mb-2">
            <button type="submit" formaction="register" formmethod="post" class="btn btn-primary">Register</button>
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
