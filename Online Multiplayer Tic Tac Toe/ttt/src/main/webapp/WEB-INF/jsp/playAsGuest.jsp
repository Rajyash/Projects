<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display Game</title>
</head>
<body>
<h2>Tic Tac Toe</h2>
<table border="1">
<c:if test="${not empty message}">
${message}
</c:if>
<c:if test="${ empty message}">
Please make your move:
</c:if>


<%-- <c:forEach items="${gb}" var="data"> --%>
<c:forEach items="${cellArray}" begin="0" end="8" varStatus="row" step="1" var="i">
<c:if test="${row.index mod 3 eq 0 }">
<tr>
</c:if>
<td height="40" width="40" align="center">
<c:if test="${ empty i }">
<c:if test="${ empty message }">

<a href="playAsGuest.html?row=${row.index}"><p>__<p></a>
</c:if>
<c:if test="${not empty message }">
<p>__<p>
</c:if>
</c:if>
<c:if test="${ not empty i }">
<c:if test="${i eq 'X' }">
<p style="color: blue">
</c:if>
<c:if test="${i eq 'O' }">
<p style="color: red">
</c:if>

${i}<p>
</c:if>

</td>
<c:if test="${(row.index+1) mod 3 eq 0}">
</tr>
</c:if>


<%-- </c:forEach> --%>
</c:forEach>

</table>
<p><a href="newGameAsGuest.html">New Game</a></p>
<p><a href="login1.html">Back</a></p>
</body>
</html>