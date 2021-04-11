<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
</head>

<body bgcolor="${pickedBgCol != null ? pickedBgCol : 'white'}">
		          
<p><a href="colors.jsp">Background color chooser</a></p>
<p><a href="trigonometric?a=0&b=90">Trigonometry</a></p>
<form action="trigonometric" method="GET">
	Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
	Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
	<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
</form>
<p><a href="stories/funny.jsp">Funny story</a></p>
<p><a href="report.jsp">OS usage report</a></p>
<p><a href="powers?a=1&b=100&n=3">Powers</a></p>
<p><a href="appinfo.jsp">Application uptime</a></p>

</body>
</html>