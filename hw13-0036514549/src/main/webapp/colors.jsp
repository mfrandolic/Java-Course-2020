<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Background color chooser</title>
</head>

<body bgcolor="${pickedBgCol != null ? pickedBgCol : 'white'}">

<h3>Choose a background color:</h3>
<a href="setcolor?color=white">WHITE</a>
<a href="setcolor?color=red">RED</a>
<a href="setcolor?color=green">GREEN</a>
<a href="setcolor?color=cyan">CYAN</a>

<p><a href="index.jsp">Home</a></p>

</body>
</html>