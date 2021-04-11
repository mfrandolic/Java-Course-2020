<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Main page</title>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/styles.css">
</head>

<body>

<%@ include file="header.jsp" %> 

<h1>Login</h1>

<form action="main" method="POST">
	<div>Nick:</div>
	<input type="text" name="nick" value="${form.nick}">
	<div class="error">${form.getError('nick')}</div>
	<br>
	
	<div>Password:</div>
	<input type="password" name="password" value="">
	<div class="error">${form.getError('password')}</div>
	<br>
	
	<input type="submit" name="button" value="Login">
	<input type="submit" name="button" value="Cancel">
</form>

<p>New here? <a href="register">Register.</a></p>

<h2>Registered authors</h2>

<c:choose>
	<c:when test="${empty authors}">
		<p>No authors.</p>
	</c:when>
	<c:otherwise>
		<ul>
			<c:forEach var="author" items="${authors}">
				<li><a href="author/${author.nick}">${author.firstName} ${author.lastName}</a></li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>

</body>
</html>
