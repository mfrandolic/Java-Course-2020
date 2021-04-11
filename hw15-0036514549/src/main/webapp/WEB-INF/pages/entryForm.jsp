<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Blog entry</title>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/styles.css">
</head>

<body>

<%@ include file="header.jsp" %>

<c:choose>
	<c:when test="${option.equals('new')}">
		<h1>New blog entry</h1>
	</c:when>
	<c:when test="${option.equals('edit')}">
		<h1>Edit blog entry</h1>
	</c:when>
</c:choose>

<form action="${option}" method="POST">
	<div>Title:</div>
	<input type="text" name="title" value="${form.title}">
	<div class="error">${form.getError('title')}</div>
	<br>
	
	<div>Text:</div>
	<textarea name="text">${form.text}</textarea>
	<div class="error">${form.getError('text')}</div>
	<br>
	
	<input type="hidden" name="id" value="${id}">
	<input type="submit" name="button" value="Save">
	<input type="submit" name="button" value="Cancel">
</form>

</body>
</html>
