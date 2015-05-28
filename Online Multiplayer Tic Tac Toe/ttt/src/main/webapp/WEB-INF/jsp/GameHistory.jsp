<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Game History</title>
</head>
<body>
<h3>Game History</h3>
<ul>
<li>
The number of games completed (i.e. excluding the saved games):${completedGames}</li>
<li>
The number of 1-player games completed:${gamesAgainstAI}
</li>
<li>
The number of 2-player games completed:${twoPlayerGames}
</li>
<li>The win rate against AI:${winRateAgainstAI}</li>
<li>The win rate against human players:${winRateAgainstHuman}</li>

</ul>

<table border="1">
<tr><th>ID</th><th>Opponent</th><th>Duration</th><th>Result</th></tr>
<c:forEach items="${gMonth}" var="monthly">
<tr><td>${ monthly.gameId}</td><td>${ monthly.opponent}</td><td>${ monthly.gameDuration}</td>
<td>
<c:if test="${empty monthly.winOrLose}">
${ monthly.gameState}
</c:if>
<c:if test="${not empty monthly.winOrLose}">
${ monthly.winOrLose }
</c:if>
</td>
</tr>
</c:forEach>
</table>

<a href="Menu.html">Back to Main Menu</a>
</body>
</html>