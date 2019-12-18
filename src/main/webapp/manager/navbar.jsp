<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand"><c:out value="${currentUser.userType.name}"/></a>

    <div class="collapse navbar-collapse" id="navbarColor01">

        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <form>
                    <button type="submit" class="nav-link" id="patients"
                            formaction="patient_list" formmethod="get">Patients
                    </button>
                </form>
            </li>
            <li class="nav-item">
                <form>
                    <button type="submit" class="nav-link" id="doctors"
                            formaction="doctor_list" formmethod="get">Doctors
                    </button>
                </form>
            </li>
            <li class="nav-item">
                <form>
                    <button type="submit" class="nav-link" id="facilities"
                            formaction="facility_statistics" formmethod="get">Facilities
                    </button>
                </form>
            </li>
            <li class="nav-item">
                <form>
                    <button type="submit" class="nav-link" id="statistics"
                            formaction="patient_statistics" formmethod="get">Statistics
                    </button>
                </form>
            </li>
        </ul>

        <form class="form-inline">
            <a class="navbar-text mr-sm-2">
                <c:out value="${sessionScope.currentUser.fullName}"/>
            </a>
            <button class="btn btn-outline-success my-2 my-sm-0"
                    type="submit" formaction="../auth/logout" formmethod="get">Logout
            </button>
        </form>

    </div>
</nav>
