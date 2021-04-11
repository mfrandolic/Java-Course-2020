<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Crtanje</title>
</head>

<body>

<p>Specifikacija nije ispravnog formata</p>

<form action="image" method="POST">
	<textarea name="text" rows="25" cols="100">${text}</textarea> <br>
	<input type="submit">
</form>

</body>
</html>