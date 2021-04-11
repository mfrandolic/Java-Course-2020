<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Registration</title>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/styles.css">
</head>

<body>

<%@ include file="header.jsp" %> 

<h1>Register</h1>

<form action="register" method="POST">
	<div>First name:</div> 
	<input type="text" name="firstName" value="${form.firstName}"> 
	<div class="error">${form.getError('firstName')}</div>
	<br>
	
	<div>Last name:</div>
	<input type="text" name="lastName" value="${form.lastName}">
	<div class="error">${form.getError('lastName')}</div>
	<br>
	
	<div>Email:</div>
	<input type="text" name="email" value="${form.email}">
	<div class="error">${form.getError('email')}</div>
	<br>
	
	<div>Nick:</div>
	<input type="text" name="nick" value="${form.nick}">
	<div class="error">${form.getError('nick')}</div>
	<br>
	
	<div>Password:</div>
	<input type="password" name="password" value="">
	<div class="error">${form.getError('password')}</div>
	<br>
	
	<input type="submit" name="button" value="Register">
	<input type="submit" name="button" value="Cancel">
</form>

</body>
</html>
