<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${sessionScope['current.user.id'] == null}">
		<p>
			Not logged in (
			<a href="${pageContext.servletContext.contextPath}/servleti/main">Login</a> 
			|
			<a href="${pageContext.servletContext.contextPath}/servleti/register">Register</a>
			)
		</p>
	</c:when>
	<c:otherwise>
		<p>
			${sessionScope['current.user.fn']} ${sessionScope['current.user.ln']} 
			(<a href="${pageContext.servletContext.contextPath}/logout">Logout</a>)
		</p>
	</c:otherwise>
</c:choose>
