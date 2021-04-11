<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Rezultati glasanja</title>
	<style type="text/css">
		table.rez {border-collapse:collapse; border-spacing: 0;}
		table.rez td {text-align: center;}
	</style>
</head>

<body>

<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja.</p>
	
<table border="1" class="rez">
	<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
	<tbody>
		<c:forEach var="band" items="${bands}">
		<tr><td>${band.name}</td><td>${band.numberOfVotes}</td></tr>
		</c:forEach>
	</tbody>
</table>
     
<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart" src="glasanje-grafika">

<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>

<c:if test="${!empty bandsWithMostVotes}">
<h2>Razno</h2>
<p>Primjeri pjesama pobjedničkih bendova:</p>
<ul>
	<c:forEach var="band" items="${bandsWithMostVotes}">
	<li><a href="${band.link}" target="_blank">${band.name}</a></li>
	</c:forEach>
</ul>
</c:if>

</body>
</html>