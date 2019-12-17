<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Registration Page</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>

    <style>
        <jsp:include page="css/login.css"/>
    </style>
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
            <button type="submit" formaction="register" formmethod="get" class="btn btn-primary">Register</button>
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
