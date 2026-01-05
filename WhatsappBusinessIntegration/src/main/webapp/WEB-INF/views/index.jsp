<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<title>WhatsApp Real Estate</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<jsp:include page="navbar.jsp" />

<div class="container text-center mt-5">
  <h1 class="fw-bold text-success">🏡 WhatsApp Real Estate Integration</h1>
  <p class="text-muted">Simulate WhatsApp chat, manage leads, properties & appointments</p>

  <div class="row mt-5">
    <div class="col-md-3">
      <a href="/chat" class="btn btn-success w-100 p-3">💬 WhatsApp Chat</a>
    </div>
   
    <div class="col-md-3">
      <a href="/properties" class="btn btn-primary w-100 p-3">🏠Properties</a>
    </div>
    <div class="col-md-3">
      <a href="/appointments" class="btn btn-info w-100 p-3">📅 Appointments</a>
    </div>
  </div>
</div>

</body>
</html>
