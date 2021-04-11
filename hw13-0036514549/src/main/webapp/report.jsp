<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Report</title>
</head>

<body bgcolor="${pickedBgCol != null ? pickedBgCol : 'white'}">

<h1>OS usage</h1>
<p>Here are the results of OS usage in survey that we completed.</p>
<img src="reportImage" alt="Pie Chart depicting OS usage.">

<p><a href="index.jsp">Home</a></p>

</body>
</html>