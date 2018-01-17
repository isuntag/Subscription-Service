<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Admin Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h1>
		Admin Dashboard
	</h1>
	<h3>Customers</h3>
	<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th>Name</th>
			<th>Next Due Date</th>
			<th>Amount Due</th>
			<th>Package Type</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${users}" var="user">
		<c:if test="${!user.checkIfAdmin()}">
			<tr>
				<td><c:out value="${user.first_name} ${user.last_name}"/></td>
				<td><fmt:formatDate type = "date" dateStyle="long" value = "${user.due_date}" /></td>
				<td><fmt:formatNumber value = "${user.apackage.cost}" type = "currency"/></td>
				<td><c:out value="${user.apackage.name}"/></td>
			</tr>
		</c:if>
		</c:forEach>
		</tbody>
	</table>
	<h3>Packages</h3>
	<table class="table table-striped table-hover table-bordered">
	<thead>
		<tr>
			<th>Package Name</th>
			<th>Package Cost</th>
			<th>Available</th>
			<th>Users</th>
			<th>Actions</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${packages}" var="thispackage">
			<tr>
				<td><c:out value="${thispackage.name}"/></td>
				<td><fmt:formatNumber value = "${thispackage.cost}" type = "currency"/></td>
				<td>
				<c:choose>
					<c:when test="${thispackage.active}">Available</c:when>
					<c:otherwise>Unavailable</c:otherwise>
				</c:choose>
				</td>
				<td><c:out value="${thispackage.getUsers().size()}"/></td>
				<td>
				<button type="button" class="btn btn-default"><a href="/packages/changeactive/${thispackage.id}">
					<c:choose>
						<c:when test="${thispackage.active}">Deactivate</c:when>
						<c:otherwise>Activate</c:otherwise>
					</c:choose>
				</a></button>
				<c:if test="${thispackage.getUsers().size() < 1}">
					<button type="button" class="btn btn-default"><a href="/packages/delete/${thispackage.id}">Delete</a></button>
				</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<form:errors path="apackage.*" />
	<form:form action="/packages/new" method="post" modelAttribute="apackage" class="form-inline">
		<div class="form-group">
			<form:label path="name">Package Name:
			<form:input path="name" class="form-control"/></form:label>
		</div>
		<div class="form-group">
			<form:label path="cost">Cost:
			<form:input class="form-control" type="number" min="0.00" step="0.01" value="0.00" path="cost"/></form:label>
		</div>
			<input class="btn btn-default" type="submit" value="Create New Package">
		</form:form>
	<form id="logoutForm" method="POST" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="submit" value="Logout" class="btn btn-default"/>
	</form>
	
</div>
</body>
</html>