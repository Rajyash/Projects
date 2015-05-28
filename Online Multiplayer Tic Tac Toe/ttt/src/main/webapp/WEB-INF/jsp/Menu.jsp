<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h3>Game Menu Page</h3>
<sec:authorize access="hasRole('ROLE_USER')">
<p><a href="DisplayInitGame.html">Play with AI</a></p><br>
<p><a href="TwoPlayerMenu.html">Play with human player</a></p><br>
<p><a href="GameHistory.html">Game History</a></p><br>
<p><a href="GamesSaved.html">Go To Saved Games</a></p><br>
<p><a href="j_spring_security_logout">Logout</a></p><br>
</sec:authorize>
</body>
</html>