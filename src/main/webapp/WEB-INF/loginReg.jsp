<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Dojoscriptions Registration Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
	<h1>Dojoscriptions</h1>
	<h3>A subscription platform</h3>
	<c:if test="${logoutMessage != null}">
		<div class="alert alert-success alert-dismissable fade in">
    			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    			<c:out value="${logoutMessage}"/>
    		</div>
	</c:if>
	<c:if test="${created != null && errorMessage == null}">
		<div class="alert alert-success alert-dismissable fade in">
    			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    			<c:out value="${created}" />
    		</div>
	</c:if>
	<h1>Login</h1>
	<c:if test="${errorMessage != null}">
		<div class="alert alert-danger alert-dismissable fade in">
    			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
    			<c:out value="${errorMessage}"/>
    		</div>
	</c:if>
	<form method="POST" action="/login" >
		<div class="form-group">
			<label for="username">Email:</label>
			<input type="text" id="username" name="username" class="form-control"/>
		</div>
		<div class="form-group">
			<label for="password">Password:</label>
			<input type="password" id="password" name="password" class="form-control"/>
		</div>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="submit" value="Login" class="btn btn-default"/>
	</form>
	<h1>Register!</h1>

	<p>
    			<form:errors path="user.*" />
	</p>

	<form:form method="POST" action="/registration" modelAttribute="user">
		<div class="form-group">
			<form:label path="email">Email:</form:label>
			<form:input path="email" class="form-control"/>
		</div>
		<div class="form-group">
			<form:label path="first_name">First Name:</form:label>
			<form:input path="first_name" class="form-control"/>
		</div>
		<div class="form-group">
			<form:label path="last_name">Last Name:</form:label>
			<form:input path="last_name" class="form-control"/>
		</div>
		<div class="form-group">
			<form:label path="password">Password:</form:label>
			<form:password path="password" class="form-control" />
		</div>
		<div class="form-group">
			<form:label path="password_confirm">Password Confirmation:</form:label>
			<form:password path="password_confirm" class="form-control"/>
		</div>
		<input type="submit" value="Register" class="btn btn-default"/>
	</form:form>
</div>
</body>
</html>