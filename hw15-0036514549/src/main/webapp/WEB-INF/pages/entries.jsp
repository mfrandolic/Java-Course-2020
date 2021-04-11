<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Blog entries</title>
</head>

<body>

<%@ include file="header.jsp" %>

<h1>Blog entries from ${author.firstName} ${author.lastName}</h1>

<c:choose>
	<c:when test="${empty entries}">
		<p>No entries.</p>
	</c:when>
	<c:otherwise>
		<ul>
			<c:forEach var="entry" items="${entries}">
				<li><a href="${author.nick}/${entry.id}">${entry.title}</a></li>
			</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>

<c:if test="${sessionScope['current.user.nick'].equals(author.nick)}">
	<p><a href="${author.nick}/new">Add new blog entry</a></p>
</c:if>

<p><a href="../main">Back to main page</a></p>

</body>
</html>
