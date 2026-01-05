<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>Properties CRUD</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-4">

<h3> Property Management</h3>

<!-- 🔹 ADD / EDIT FORM -->
<div class="card mb-4">
<div class="card-header fw-bold">
${property.id == null ? " Add Property" : "✏️ Edit Property"}
</div>

<div class="card-body">
<form action="/properties/save" method="post">

<input type="hidden" name="id" value="${property.id}">

<div class="row">
  <div class="col-md-4">
    <input class="form-control mb-2" name="title"
           value="${property.title}" placeholder="Title" required>
  </div>

  <div class="col-md-3">
    <input class="form-control mb-2" name="city"
           value="${property.city}" placeholder="City" required>
  </div>

  <div class="col-md-3">
    <input class="form-control mb-2" name="price"
           value="${property.price}" placeholder="Price" required>
  </div>

  <div class="col-md-2">
    <select class="form-control mb-2" name="available">
      <option value="true" ${property.available ? "selected" : ""}>Available</option>
      <option value="false" ${!property.available ? "selected" : ""}>Not Available</option>
    </select>
  </div>
</div>

<input class="form-control mb-2" name="propertyType"
       value="${property.propertyType}" placeholder="Property Type">

<input class="form-control mb-2" name="imageUrl"
       value="${property.imageUrl}" placeholder="Image URL">

<button class="btn btn-success">
${property.id == null ? "Save" : "Update"}
</button>

<c:if test="${property.id != null}">
  <a href="/properties" class="btn btn-secondary">Cancel</a>
</c:if>

</form>
</div>
</div>

<!-- 🔹 TABLE -->
<table class="table table-bordered table-striped">
<thead class="table-dark">
<tr>
<th>Image</th>
<th>Title</th>
<th>City</th>
<th>Price</th>
<th>Status</th>
<th>Actions</th>
</tr>
</thead>

<tbody>
<c:forEach items="${properties}" var="p">
<tr>
<td>
<img src="${p.imageUrl}" style="height:60px;width:90px;object-fit:cover">
</td>
<td>${p.title}</td>
<td> ${p.city}</td>
<td>rs ${p.price}</td>
<td>
<span class="badge ${p.available ? 'bg-success' : 'bg-danger'}">
${p.available ? "Available" : "Not Available"}
</span>
</td>
<td>
<a href="/properties/edit/${p.id}" class="btn btn-warning btn-sm">Edit</a>
<a href="/properties/delete/${p.id}"
   onclick="return confirm('Delete this property?')"
   class="btn btn-danger btn-sm">Delete</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>

<a href="/" class="btn btn-secondary mt-3">Back to home</a>

</div>
</body>
</html>
