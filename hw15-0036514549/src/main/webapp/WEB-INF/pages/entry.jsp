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

<h1>${entry.title}</h1>

<div>Created: ${entry.createdAt}</div>
<div>Last modified: ${entry.lastModifiedAt}</div>
<div>Author: ${entry.creator.firstName} ${entry.creator.lastName}</div>

<p>${entry.text}</p>

<c:if test="${sessionScope['current.user.nick'].equals(author.nick)}">
	<p><a href="edit?id=${entry.id}">Edit blog entry</a></p>
</c:if>

<h2>Comments</h2>

<c:choose>
	<c:when test="${empty comments}">
		<p>No comments.</p>
	</c:when>
	<c:otherwise>
		<dl>
			<c:forEach var="comment" items="${comments}">
				<dt>${comment.usersEmail} (${comment.postedOn})<dt>
				<dd>${comment.message}</dd>
			</c:forEach>
		</dl>
	</c:otherwise>
</c:choose>

<h2>Add new comment</h2>

<form action="${entry.id}" method="POST">
	<div>Message:</div>
	<textarea name="message">${form.message}</textarea>
	<div class="error">${form.getError('message')}</div>
	<br>
	
	<input type="submit" name="button" value="Post">
	<input type="submit" name="button" value="Cancel">
</form>

<p><a href="../${entry.creator.nick}">
	Back to author's blog entries
</a></p>

</body>
</html>
