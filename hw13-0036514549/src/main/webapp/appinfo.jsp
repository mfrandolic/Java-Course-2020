<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Application uptime</title>
</head>

<body bgcolor="${pickedBgCol != null ? pickedBgCol : 'white'}">

<h3>How long is this web application running?</h3>

<% 
long startTime = (Long) application.getAttribute("uptime"); 
long currentTime = System.currentTimeMillis();

long milliseconds = currentTime - startTime;
long seconds = milliseconds / 1000;
long minutes = seconds / 60;
long hours = minutes / 60;
long days = hours / 24;

StringBuilder sb = new StringBuilder();
sb.append(days != 0 ? days + " days " : "");
sb.append(hours != 0 ? hours % 24 + " hours " : "");
sb.append(minutes != 0 ? minutes % 60 + " minutes " : "");
sb.append(seconds != 0 ? seconds % 60 + " seconds " : "");
sb.append(milliseconds % 1000 + " milliseconds");

out.write("<p>" + sb.toString() + "</p>");
%>

<p><a href="index.jsp">Home</a></p>

</body>
</html>