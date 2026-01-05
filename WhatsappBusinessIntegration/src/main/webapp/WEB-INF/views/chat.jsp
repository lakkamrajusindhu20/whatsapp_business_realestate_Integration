<%@ page session="true" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<title>WhatsApp Chat</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
.chat-box {
    height: 350px;
    overflow-y: auto;
    background: #e5ddd5;
    padding: 15px;
    border-radius: 8px;
}
.msg-bot {
    background:#dcf8c6;
    padding:8px;
    border-radius:10px;
    margin:5px;
}
</style>
</head>

<body>
<jsp:include page="navbar.jsp" />

<div class="container">
<h3>💬 WhatsApp Chat Simulation</h3>

<div class="chat-box mb-3">
    <c:if test="${response eq 'SCHEDULE_APPOINTMENT'}">
        <div class="msg-bot">
            📅 Please click below to schedule your appointment
            <br><br>
            <a href="${pageContext.request.contextPath}/appointments/new"
               class="btn btn-success btn-sm">
               Schedule Appointment
            </a>
        </div>
    </c:if>
	<c:if test="${not empty sessionScope.appointmentSuccess}">
	    <div class="msg-bot">
	        ${sessionScope.appointmentSuccess}
	    </div>
	    <c:remove var="appointmentSuccess" scope="session"/>
	</c:if>


    <c:if test="${not empty response and response ne 'SCHEDULE_APPOINTMENT'}">
		<div class="msg-bot">
		    <c:out value="${response}" escapeXml="false"/>
		</div>
    </c:if>
</div>

<form action="${pageContext.request.contextPath}/chat/send" method="post">
    <input class="form-control mb-2" name="phoneNumber" placeholder="Phone Number" required>
    <select class="form-control mb-2" name="language">
        <option value="EN">English</option>
        <option value="HI">Hindi</option>
        <option value="TE">Telugu</option>
    </select>
    <input class="form-control mb-2" name="message" placeholder="Type Buy / Rent / City / Budget">
    <button class="btn btn-success w-100">Send</button>
</form>
</div>
</body>
</html>

