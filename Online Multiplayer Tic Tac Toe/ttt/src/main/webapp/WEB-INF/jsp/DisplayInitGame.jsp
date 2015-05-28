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


<c:forEach items="${cell}" begin="0" end="8" var="i" varStatus="row">
	<c:if test="${row.index mod 3 eq 0 }">
<tr>
</c:if>
<td height="40" width="40" align="center">
<a href="DisplayGame.html?row=${row.index}"><p>__<p></a>
</td>
<c:if test="${(row.index+1) mod 3 eq 0}">
</tr>
</c:if>

</c:forEach>
</table>
<!-- <p><a href="DisplayInitGame.html">New Game</a></p>
<p><a href="logout.html">Logout</a></p>
 -->

<p><a href="SaveGame.html?row=9999">Save Game</a></p>
<p><a href="newGame.html?row=99">New Game</a></p>
<p><a href="logout.html?row=999">Logout</a></p>
</body>
</html>