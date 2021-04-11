<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Trigonometry</title>
</head>

<body bgcolor="${pickedBgCol != null ? pickedBgCol : 'white'}">

<table border="1">
	<thead>
		<tr><th>x</th><th>sin(x)</th><th>cos(x)</th></tr>
	</thead>
	
	<c:forEach var="i" begin="0" end="${b - a}">
	<tbody>
		<tr><td>${i + a}</td><td>${sines[i]}</td><td>${cosines[i]}</td></tr>
	</tbody>
	</c:forEach>
</table>

<p><a href="index.jsp">Home</a></p>

</body>
</html>