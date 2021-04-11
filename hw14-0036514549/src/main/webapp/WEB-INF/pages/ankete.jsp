<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Ankete</title>
</head>

<body>

<h1>Dostupne ankete:</h1>

<dl>
	<c:forEach var="poll" items="${polls}">
	<dt><a href="glasanje?pollID=${poll.id}">${poll.title}</a></dt>
	<dd>${poll.message}</dd>
	</c:forEach>
</dl>

</body>
</html>