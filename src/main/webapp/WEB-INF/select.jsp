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
	<h1>Welcome to Dojoscriptions <c:out value="${currentUser.first_name}"/></h1>
	<c:choose>
	<c:when test="${packages.size() > 0 && active==true}">
	<p>Please choose a subscription and a start date.</p>
	<c:if test="${errors != null}">
		<c:out value="${errors}"></c:out>
	</c:if>
	<form action="/users/${currentUser.id}" method="POST">
			<div class="form-group">
				<label for="due">Due Date:</label>
				<input type="number" name="due" min="1" max="31" step="1">
			</div>
			<div class="form-group">
				<label for="apackage">Package:</label>
				<select name="apackage">
					<c:forEach items="${packages}" var="thispackage">
						<c:if test="${thispackage.active}">
        					<option value="${thispackage.id}"><c:out value="${thispackage.name}"/> (<fmt:formatNumber value = "${thispackage.cost}" type = "currency"/>)</option>
        					</c:if>
   					</c:forEach>
				</select>
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
			<input type="submit" value="Sign Up!" class="btn btn-default">
		</form>
	</c:when>
	<c:otherwise>
	<p>There are no packages available. Please contact admin.</p>
	</c:otherwise>
	</c:choose>
	<form id="logoutForm" method="POST" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="submit" value="Logout" class="btn btn-default"/>
	</form>
</div>
</body>
</html>