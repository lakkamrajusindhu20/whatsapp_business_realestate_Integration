<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Schedule Appointment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container mt-4">
    <h3 class="mb-4">📅 Schedule Property Visit</h3>

    <form action="/appointments/save" method="post" class="card p-4 shadow-sm">

        <!-- PHONE NUMBER (FROM SESSION) -->
        <div class="mb-3">
            <label class="form-label">Phone Number</label>
            <input type="number"
                   class="form-control"
                   value="${sessionScope.lead.phoneNumber}"
                   readonly >
        </div>

        <!-- PROPERTY DROPDOWN (REQUIRED) -->
		<div class="mb-3">
		<label class="form-label">Select Property</label>
		<select class="form-control" name="propertyId" required>
		    <option value="">-- Select Property --</option>
		    <c:forEach var="p" items="${properties}">
		        <option value="${p.id}">
		            ${p.title} - ${p.city}
		        </option>
		    </c:forEach>
		</select>
		</div>


        <!-- VISIT DATE -->
        <div class="mb-3">
            <label class="form-label">Visit Date</label>
            <input type="date"
                   class="form-control"
                   name="visitDate"
                   required>
        </div>

        <!-- VISIT TIME -->
        <div class="mb-3">
            <label class="form-label">Visit Time</label>
            <input type="time"
                   class="form-control"
                   name="visitTime"
                   required>
        </div>

        <button class="btn btn-success w-100">
            ✅ Confirm Appointment
        </button>

    </form>
</div>

</body>
</html>
