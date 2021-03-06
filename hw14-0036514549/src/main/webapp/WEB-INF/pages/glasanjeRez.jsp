<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
	<thead><tr><th>Opcija</th><th>Broj glasova</th></tr></thead>
	<tbody>
		<c:forEach var="pollOption" items="${pollOptions}">
		<tr><td>${pollOption.optionTitle}</td><td>${pollOption.votesCount}</td></tr>
		</c:forEach>
	</tbody>
</table>
     
<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart" src="glasanje-grafika?pollID=${pollID}">

<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${pollID}">ovdje</a></p>

<h2>Razno</h2>
<p>Linkovi na pobjedničke opcije:</p>
<ul>
	<c:forEach var="pollOption" items="${pollOptionsWithMostVotes}">
	<li><a href="${pollOption.optionLink}" target="_blank">${pollOption.optionTitle}</a></li>
	</c:forEach>
</ul>

</body>
</html>