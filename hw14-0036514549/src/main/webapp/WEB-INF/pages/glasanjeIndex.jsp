<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Glasanje</title>
</head>

<body>

<h1>${poll.title}</h1>
<p>${poll.message}</p>

<ol>
	<c:forEach var="pollOption" items="${pollOptions}">
	<li><a href="glasanje-glasaj?id=${pollOption.id}">${pollOption.optionTitle}</a></li>
	</c:forEach>
</ol>
	
</body>
</html>