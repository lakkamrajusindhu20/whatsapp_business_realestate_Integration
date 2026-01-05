<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>Add Property</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<jsp:include page="navbar.jsp" />

<div class="container">
<h3>Add Property</h3>

<form action="/properties/save" method="post">

<input class="form-control mb-2" name="title" placeholder="Title" required>
<input class="form-control mb-2" name="description" placeholder="Description">
<input class="form-control mb-2" name="city" placeholder="City" required>
<input class="form-control mb-2" name="area" placeholder="Area">
<input class="form-control mb-2" name="address" placeholder="Address">
<input class="form-control mb-2" name="propertyType" placeholder="Type">
<input class="form-control mb-2" name="areaSqFt" placeholder="Area SqFt">
<input class="form-control mb-2" name="price" placeholder="Price">
<input class="form-control mb-2" name="priceType" placeholder="Price Type">
<input class="form-control mb-2" name="imageUrl" placeholder="Image URL">

<select class="form-control mb-3" name="available">
  <option value="true">Available</option>
  <option value="false">Not Available</option>
</select>

<button class="btn btn-warning">Save Property</button>
</form>
</div>

</body>
</html>
