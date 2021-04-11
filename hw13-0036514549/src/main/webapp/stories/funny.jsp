<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
    session="true" import="java.util.Random" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
String[] colors = new String[] {"black", "red", "green", "blue", "orange", "yellow"};
pageContext.setAttribute("textColor", colors[new Random().nextInt(colors.length)]);
%>

<!DOCTYPE html>
<html>
<head>
	<title>Funny story</title>
	<style>
		p { color: ${textColor}; }
	</style>
</head>

<body bgcolor="${pickedBgCol != null ? pickedBgCol : 'white'}">

<p>My life.</p>

<p><a href="../index.jsp">Home</a></p>

</body>
</html>