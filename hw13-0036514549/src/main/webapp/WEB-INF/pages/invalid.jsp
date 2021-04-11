<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Invalid parameters</title>
</head>

<body bgcolor="${pickedBgCol != null ? pickedBgCol : 'white'}">

<h3>Invalid parameter:</h3>
<p>${exceptionMessage}</p>

<p><a href="index.jsp">Home</a></p>

</body>
</html>